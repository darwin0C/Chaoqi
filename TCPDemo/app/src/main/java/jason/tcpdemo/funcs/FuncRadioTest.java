package jason.tcpdemo.funcs;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TabHost;
import jason.tcpdemo.R;

import android.widget.TextView;
import java.lang.ref.WeakReference;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jason.tcpdemo.MainActivity;
/**
 * Created by Chao on 2018-09-14.
 */
public class FuncRadioTest extends Activity{
    private String TAG = "FuncRadioTest";
    public static Context context ;
    private TextView textDemotest;
    private String strMessage;
    private TextView textlfPull,textrhPull,textlockA,textlockB,textRangeAngle,textLfAngle,textRhAngle;

    private final MyHandler myHandler = new MyHandler(this);
    private FuncRadioTest.MyBroadcastReceiver myBroadcastReceiver = new FuncRadioTest.MyBroadcastReceiver();
    ExecutorService exec = Executors.newCachedThreadPool();

    @SuppressLint("StaticFieldLeak")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radiolayout);
        context = this;

        //bindID();
        //bindListener();
        //
        //bindReceiver();
         }
    private class MyHandler extends android.os.Handler{
        private WeakReference<FuncRadioTest> mActivity;

        MyHandler(FuncRadioTest activity){
            mActivity = new WeakReference<FuncRadioTest>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity != null){
                switch (msg.what){
                    case 1:
                        //txtRcv.append(msg.obj.toString());
                        //textlfPull.setText(msg.obj.toString());
                        textDemotest.setText(msg.obj.toString());
                        processMessage(msg.obj.toString());
                        break;
                    case 2:
                        //txtSend.append(msg.obj.toString());
                        break;
                }
            }
        }
    }
    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String mAction = intent.getAction();
            switch (mAction){
                case "tcpClientReceiver":
                    String msg = intent.getStringExtra("tcpClientReceiver");
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = msg;
                    myHandler.sendMessage(message);
                    break;
            }
        }
    }
    private void bindReceiver(){
        IntentFilter intentFilter = new IntentFilter("tcpClientReceiver");
        registerReceiver(myBroadcastReceiver,intentFilter);
    }
    private void bindID(){
        //卷扬速度 btnRoalSpeed1,btnRoalSpeed2,btnRoalSpeed3;
        //btnRoalSpeed1 = (Button) findViewById(R.id.suspeed1);
        //textlfPull,textrhPull,textlockA,textlockB,textRangeAngle,textLfAngle,textRhAngle;
        //textlfPull=(TextView)findViewById(R.id.textleftpull);
        textDemotest=(TextView)findViewById(R.id.textDemo);

    }

    private void bindListener(){
        //btnRoalSpeed1.setOnClickListener(myBtnClicker);
    }
    private void processMessage(String messageRev) {
        String dest = "";
    }
    private void SendMessage(String stringtosend)
    {
        strMessage=stringtosend;
        Message message = Message.obtain();
        message.what = 2;
        message.obj = stringtosend;
        myHandler.sendMessage(message);
        exec.execute(new Runnable() {
            @Override
            public void run() {
                MainActivity.tcpClient.send(strMessage);
            }
        });
    }
}
