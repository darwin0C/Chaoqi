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
import android.widget.Toast;
import java.lang.ref.WeakReference;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jason.tcpdemo.MainActivity;
/**
 * Created by Chao on 2018-03-15.
 */

public class FuncAItest extends Activity{
    private String TAG = "FuncAITest";
    public static Context context ;
    private TextView textlfPull,textrhPull,textlockA,textlockB,textRangeAngle,textLfAngle,textRhAngle;

    private final MyHandler myHandler = new MyHandler(this);
    private MyBroadcastReceiver myBroadcastReceiver = new FuncAItest.MyBroadcastReceiver();
    ExecutorService exec = Executors.newCachedThreadPool();

    @SuppressLint("StaticFieldLeak")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aitestlayout);
        context = this;



        bindID();
        bindListener();
        //
        bindReceiver();
    }
    private class MyHandler extends android.os.Handler{
        private WeakReference<FuncAItest> mActivity;

        MyHandler(FuncAItest activity){
            mActivity = new WeakReference<FuncAItest>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity != null){
                switch (msg.what){
                    case 1:
                        //txtRcv.append(msg.obj.toString());
                        //textlfPull.setText(msg.obj.toString());
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
        textlfPull=(TextView)findViewById(R.id.textleftpull);
        textrhPull=(TextView)findViewById(R.id.textrightpull);
        textlockA=(TextView)findViewById(R.id.textlockpressA);
        textlockB=(TextView)findViewById(R.id.textlockpressB);
        textRangeAngle=(TextView)findViewById(R.id.textRangeAngle);
        textLfAngle=(TextView)findViewById(R.id.textLeftAngle);
        textRhAngle=(TextView)findViewById(R.id.textRightAngle);

    }

    private void bindListener(){
        //btnRoalSpeed1.setOnClickListener(myBtnClicker);
    }
    private void processMessage(String messageRev) {
        String dest = "";
        if (messageRev != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(messageRev);
            dest = m.replaceAll("");
        }
        switch (dest) {
            case "b6a":
                textlfPull.setText("1");
                break;
            case "b6b":
                textrhPull.setText("1");
                break;
            case "b20":
                textlockA.setText("1");
                break;
            case "b21":
                textlockB.setText("1");
                break;
            case "b87":
                textRangeAngle.setText("1");
                break;
            case "b90":
                textLfAngle.setText("1");
                break;
            case "b91":
                textRhAngle.setText("1");
                break;
        }
    }
}
