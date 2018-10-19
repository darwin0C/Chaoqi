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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jason.tcpdemo.MainActivity;
/**
 * Created by Chao on 2018-03-14.
 */

public class FuncTest extends Activity {
    private String TAG = "FuncTest";
    public static Context context ;
    private Button btnsuARoalUP,btnsuARoalDown,btnsuAopen,btnsuAClose,btnsuALockOut,btnsuALockIn;
    private Button btnsuBRoalUP,btnsuBRoalDown,btnsuBopen,btnsuBClose,btnsuBLockOut,btnsuBLockIn;
    private Button btnsuRangeUp,btnsuRangeDown,btnsuRoalFloat,btnsuLockChoose,btnsuLockPress,btnsuRoalDownPress;
    private Button btnRoalSpeed1,btnRoalSpeed2,btnRoalSpeed3;
    private TextView textDemotest;
    private String strMessage;
    private FuncTest.MyBtnClicker myBtnClicker = new FuncTest.MyBtnClicker();

    private final MyHandler myHandler = new MyHandler(this);
    private MyBroadcastReceiver myBroadcastReceiver = new FuncTest.MyBroadcastReceiver();
    ExecutorService exec = Executors.newCachedThreadPool();

    @SuppressLint("StaticFieldLeak")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);
        context = this;

        TabHost tab = (TabHost) findViewById(android.R.id.tabhost);

        //初始化TabHost容器
        tab.setup();

        //在TabHost创建标签，然后设置：标题／图标／标签页布局
        tab.addTab(tab.newTabSpec("tab1").setIndicator("超起A" , null).setContent(R.id.tab1));
        tab.addTab(tab.newTabSpec("tab2").setIndicator("超起B" , null).setContent(R.id.tab2));
        tab.addTab(tab.newTabSpec("tab3").setIndicator("超起其他动作" , null).setContent(R.id.tab3));

        bindID();
        bindListener();
        bindReceiver();
    }
    private class MyHandler extends android.os.Handler{
        private WeakReference<FuncTest> mActivity;

        MyHandler(FuncTest activity){
            mActivity = new WeakReference<FuncTest>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity != null){
                switch (msg.what){
                    case 1:
                        //txtRcv.append(msg.obj.toString());
                        textDemotest.setText(msg.obj.toString());
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
    private class MyBtnClicker implements View.OnClickListener{
        public void onClick(View view) {
            Toast toast=null;
            switch (view.getId()){
             case R.id.btn_tcpClientConn:
                Log.i(TAG, "onClick: 开始");

                break;
                //卷扬速度
                case R.id.suspeed1:
                    SendMessage("speed1");
                    toast=Toast.makeText(getApplicationContext(),"速度1",Toast.LENGTH_LONG);
                     break;
                case R.id.suspeed2:
                    SendMessage("speed2");
                    toast=Toast.makeText(getApplicationContext(),"速度2",Toast.LENGTH_LONG);
                    break;
                case R.id.suspeed3:
                    SendMessage("speed3");
                    toast=Toast.makeText(getApplicationContext(),"速度3",Toast.LENGTH_LONG);
                    break;
                //超起A
                case R.id.suAroalup:
                    SendMessage("suAroalup");
                    toast=Toast.makeText(getApplicationContext(),"卷扬A起",Toast.LENGTH_LONG);
                     break;
                case R.id.suAroaldown:
                    SendMessage("suAroaldown");
                    toast=Toast.makeText(getApplicationContext(),"卷扬A落",Toast.LENGTH_LONG);
                    break;
                case R.id.suAopen:
                    SendMessage("suAopen");
                    toast=Toast.makeText(getApplicationContext(),"超起装置A展开",Toast.LENGTH_LONG);
                    break;
                case R.id.suAclose:
                    SendMessage("suAclose");
                    toast=Toast.makeText(getApplicationContext(),"超起装置A收回",Toast.LENGTH_LONG);
                    break;
                case R.id.suAjlstretch:
                    SendMessage("suAjlstretch");
                    toast=Toast.makeText(getApplicationContext(),"棘轮锁止油缸A伸",Toast.LENGTH_LONG);
                    break;
                case R.id.suAjlretraction:
                    SendMessage("suAjlretraction");
                    toast=Toast.makeText(getApplicationContext(),"棘轮锁止油缸A缩",Toast.LENGTH_LONG);
                    break;
                //超起B
                case R.id.suBroalup:
                    SendMessage("suBroalup");
                    toast=Toast.makeText(getApplicationContext(),"卷扬B起",Toast.LENGTH_LONG);
                    break;
                case R.id.suBroaldown:
                    SendMessage("suBroaldown");
                    toast=Toast.makeText(getApplicationContext(),"卷扬B落",Toast.LENGTH_LONG);
                    break;
                case R.id.suBopen:
                    SendMessage("suBopen");
                    toast=Toast.makeText(getApplicationContext(),"超起装置B展开",Toast.LENGTH_LONG);
                    break;
                case R.id.suBclose:
                    SendMessage("suBclose");
                    toast=Toast.makeText(getApplicationContext(),"超起装置B收回",Toast.LENGTH_LONG);
                    break;
                case R.id.suBjlstretch:
                    SendMessage("suBjlstretch");
                    toast=Toast.makeText(getApplicationContext(),"棘轮锁止油缸B伸",Toast.LENGTH_LONG);
                    break;
                case R.id.suBjlretraction:
                    SendMessage("suBjlretraction");
                    toast=Toast.makeText(getApplicationContext(),"棘轮锁止油缸B缩",Toast.LENGTH_LONG);
                break;
                //超起其他动作
                case R.id.suRangeUp:
                    SendMessage("suRangeUp");
                    toast=Toast.makeText(getApplicationContext(),"超起变幅起",Toast.LENGTH_LONG);
                     break;
                case R.id.suRangeDown:
                    SendMessage("suRangeDown");
                    toast=Toast.makeText(getApplicationContext(),"超起变幅落",Toast.LENGTH_LONG);
                    break;
                case R.id.suRoalFloat:
                    SendMessage("suRoalFloat");
                    toast=Toast.makeText(getApplicationContext(),"超起卷扬浮动",Toast.LENGTH_LONG);
                    break;
                case R.id.sujlLockChoose:
                    SendMessage("sujlLockChoose");
                    toast=Toast.makeText(getApplicationContext(),"棘轮锁止油缸选择",Toast.LENGTH_LONG);
                    break;
                case R.id.suLockPress:
                    SendMessage("suLockPress");
                    toast=Toast.makeText(getApplicationContext(),"棘轮锁止油缸压力",Toast.LENGTH_LONG);
                    break;
                case R.id.suRoalDownPress:
                    SendMessage("suRoalDownPress");
                    toast=Toast.makeText(getApplicationContext(),"超起卷扬落压力",Toast.LENGTH_LONG);
                    break;
            }
            showMyToast(toast,1000);
        }
    }
    private void bindID(){
        //卷扬速度 btnRoalSpeed1,btnRoalSpeed2,btnRoalSpeed3;
        btnRoalSpeed1 = (Button) findViewById(R.id.suspeed1);
        btnRoalSpeed2 = (Button) findViewById(R.id.suspeed2);
        btnRoalSpeed3 = (Button) findViewById(R.id.suspeed3);

        //btnsuARoalUP,btnsuARoalDown,btnsuAopen, btnsuAClose,btnsuALockOut,btnsuALockIn;
        //超起A
        btnsuARoalUP = (Button) findViewById(R.id.suAroalup);
        btnsuARoalDown = (Button) findViewById(R.id.suAroaldown);
        btnsuAopen = (Button) findViewById(R.id.suAopen);

        btnsuAClose = (Button) findViewById(R.id.suAclose);
        btnsuALockOut = (Button) findViewById(R.id.suAjlstretch);
        btnsuALockIn = (Button) findViewById(R.id.suAjlretraction);
        //btnsuBRoalUP,btnsuBRoalDown,btnsuBopen,btnsuBClose,btnsuBLockOut,btnsuBLockIn;
        //超起B
        btnsuBRoalUP = (Button) findViewById(R.id.suBroalup);
        btnsuBRoalDown = (Button) findViewById(R.id.suBroaldown);
        btnsuBopen = (Button) findViewById(R.id.suBopen);
        btnsuBClose = (Button) findViewById(R.id.suBclose);
        btnsuBLockOut = (Button) findViewById(R.id.suBjlstretch);
        btnsuBLockIn = (Button) findViewById(R.id.suBjlretraction);
        //超起其他动作
        //btnsuRangeUp,btnsuRangeDown,btnsuRoalFloat,  btnsuLockChoose,btnsuLockPress,btnsuRoalDownPress;
        btnsuRangeUp = (Button) findViewById(R.id.suRangeUp);
        btnsuRangeDown = (Button) findViewById(R.id.suRangeDown);
        btnsuRoalFloat = (Button) findViewById(R.id.suRoalFloat);

        btnsuLockChoose = (Button) findViewById(R.id.sujlLockChoose);
        btnsuLockPress = (Button) findViewById(R.id.suLockPress);
        btnsuRoalDownPress = (Button) findViewById(R.id.suRoalDownPress);

        textDemotest=(TextView)findViewById(R.id.textDemo);
    }

    private void bindListener(){
       // btnStartClient.setOnClickListener(myBtnClicker);
        //卷扬速度 btnRoalSpeed1,btnRoalSpeed2,btnRoalSpeed3;
        btnRoalSpeed1.setOnClickListener(myBtnClicker);
        btnRoalSpeed2.setOnClickListener(myBtnClicker);
        btnRoalSpeed3.setOnClickListener(myBtnClicker);
        //btnsuARoalUP,btnsuARoalDown,btnsuAopen, btnsuAClose,btnsuALockOut,btnsuALockIn;
        //超起A
        btnsuARoalUP.setOnClickListener(myBtnClicker);
        btnsuARoalDown.setOnClickListener(myBtnClicker);
        btnsuAopen.setOnClickListener(myBtnClicker);

        btnsuAClose.setOnClickListener(myBtnClicker);
        btnsuALockOut.setOnClickListener(myBtnClicker);
        btnsuALockIn.setOnClickListener(myBtnClicker);
        //btnsuBRoalUP,btnsuBRoalDown,btnsuBopen,btnsuBClose,btnsuBLockOut,btnsuBLockIn;
        //超起B
        btnsuBRoalUP.setOnClickListener(myBtnClicker);
        btnsuBRoalDown.setOnClickListener(myBtnClicker);
        btnsuBopen.setOnClickListener(myBtnClicker);

        btnsuBClose.setOnClickListener(myBtnClicker);
        btnsuBLockOut.setOnClickListener(myBtnClicker);
        btnsuBLockIn.setOnClickListener(myBtnClicker);
        //超起其他动作
        //btnsuRangeUp,btnsuRangeDown,btnsuRoalFloat,  btnsuLockChoose,btnsuLockPress,btnsuRoalDownPress;
        btnsuRangeUp.setOnClickListener(myBtnClicker);
        btnsuRangeDown.setOnClickListener(myBtnClicker);
        btnsuRoalFloat.setOnClickListener(myBtnClicker);

        btnsuLockChoose.setOnClickListener(myBtnClicker);
        btnsuLockPress.setOnClickListener(myBtnClicker);
        btnsuRoalDownPress.setOnClickListener(myBtnClicker);

    }

    public void showMyToast(final Toast toast,final int cnt)
    {
        final Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        },0,500);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        },cnt);
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
