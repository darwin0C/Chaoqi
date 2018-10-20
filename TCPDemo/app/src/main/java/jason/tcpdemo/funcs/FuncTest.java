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

public class
FuncTest extends Activity {
    private String TAG = "FuncTest";
    public static Context context ;
    private Button Y25a1,Y24b1,Y24a1,Y39b1,Y39a1,Y40b1,Y40a1,Y28b1,Y28a1,Y43b1,Y43a1,Y42b1,Y42a1;
    private Button Y25a2,Y24b2,Y24a2,Y39b2,Y39a2,Y40b2,Y40a2,Y28b2,Y28a2,Y43b2,Y43a2,Y42b2,Y42a2;
    private Button Y45b1,Y45a1,Y41a1,Y29a1,Y50c1,Y44a1;

    private Button btnRoalSpeed1;
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
                        String strShow=msg.obj.toString();
                        if(strShow.length()<20)
                        textDemotest.setText("连接成功！");
                        break;
                    case 2:
                        //txtSend.append(msg.obj.toString());
                        break;
                }
            }
        }
    }
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
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
            byte[] bytesSend=new byte[7];
            bytesSend[0]=(byte)0xfe;
            bytesSend[1]=(byte)0x02;

                    //{(byte)0xfe,(byte)0x02,(byte)0xff,(byte)0xff,(byte)0xff};
            switch (view.getId()){

             case R.id.btn_tcpClientConn:
                Log.i(TAG, "onClick: 开始");
                break;
                //复位
                case R.id.suspeed1:
                    //SendMessage("suAroalup");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"复位",Toast.LENGTH_LONG);
                    break;
                //超起A
                case R.id.Y45b1:
                    //SendMessage("suAroalup");
                    bytesSend[2]=(byte)0x80;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y45b1",Toast.LENGTH_LONG);
                     break;
                case R.id.Y45a1:
                    //SendMessage("suAroaldown");
                    bytesSend[2]=(byte)0x40;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y45a1",Toast.LENGTH_LONG);
                    break;
                case R.id.Y25a1:
                    //SendMessage("suAopen");
                    bytesSend[2]=(byte)0x20;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y25a1",Toast.LENGTH_LONG);
                    break;
                case R.id.Y24b1:
                    //SendMessage("suAclose");
                    bytesSend[2]=(byte)0x08;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y24b1",Toast.LENGTH_LONG);
                    break;
                case R.id.Y24a1:
                    //SendMessage("suAjlstretch");
                    bytesSend[2]=(byte)0x04;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y24a1",Toast.LENGTH_LONG);
                    break;
                case R.id.Y41a1:
                    //SendMessage("suAjlretraction");
                    bytesSend[2]=(byte)0x02;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y41a1",Toast.LENGTH_LONG);
                    break;
                case R.id.Y39b1:
                    //SendMessage("suAroalup");
                    bytesSend[2]=(byte)0x01;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y39b1",Toast.LENGTH_LONG);
                    break;

                case R.id.Y39a1:
                    //SendMessage("suAroaldown");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x80;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y39a1",Toast.LENGTH_LONG);
                    break;
                case R.id.Y40b1:
                    //SendMessage("suAopen");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x40;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y40b1",Toast.LENGTH_LONG);
                    break;
                case R.id.Y40a1:
                    //SendMessage("suAclose");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x20;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y40a1",Toast.LENGTH_LONG);
                    break;
                case R.id.Y29a1:
                    //SendMessage("suAjlstretch");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x10;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y29a1",Toast.LENGTH_LONG);
                    break;
                case R.id.Y28b1:
                    //SendMessage("suAjlretraction");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x08;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y28b1",Toast.LENGTH_LONG);
                    break;
                case R.id.Y28a1:
                    //SendMessage("suAjlretraction");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x04;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y28a1",Toast.LENGTH_LONG);
                    break;

                case R.id.Y43b1:
                    //SendMessage("suBroalup");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x02;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"卷扬B起",Toast.LENGTH_LONG);
                    break;
                case R.id.Y43a1:
                    //SendMessage("suBroaldown");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x01;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y43a1",Toast.LENGTH_LONG);
                    break;

                case R.id.Y42b1:
                    //SendMessage("suBopen");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x80;
                    toast=Toast.makeText(getApplicationContext(),"Y42b1",Toast.LENGTH_LONG);
                    break;
                case R.id.Y42a1:
                    //SendMessage("suBclose");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x40;
                    toast=Toast.makeText(getApplicationContext(),"Y42a1",Toast.LENGTH_LONG);
                    break;
                case R.id.Y50c1:
                    //SendMessage("suBjlstretch");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x20;
                    toast=Toast.makeText(getApplicationContext(),"Y50c1",Toast.LENGTH_LONG);
                    break;
                case R.id.Y44a1:
                    //SendMessage("suBjlretraction");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x10;
                    toast=Toast.makeText(getApplicationContext(),"Y44a1",Toast.LENGTH_LONG);
                break;
                //超起B
                case R.id.Y25a2:
                    //SendMessage("suRangeUp");
                    bytesSend[2]=(byte)0x20;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y25a2",Toast.LENGTH_LONG);
                     break;
                case R.id.Y24b2:
                    //SendMessage("suRangeDown");
                    bytesSend[2]=(byte)0x08;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y24b2",Toast.LENGTH_LONG);
                    break;
                case R.id.Y24a2:
                    //SendMessage("suRoalFloat");
                    bytesSend[2]=(byte)0x04;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y24a2",Toast.LENGTH_LONG);
                    break;
                case R.id.Y39b2:
                    //SendMessage("sujlLockChoose");
                    bytesSend[2]=(byte)0x01;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y39b2",Toast.LENGTH_LONG);
                    break;

                case R.id.Y39a2:
                    //SendMessage("suLockPress");
                    bytesSend[2]=(byte)0x01;
                    bytesSend[3]=(byte)0x80;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y39a2",Toast.LENGTH_LONG);
                    break;
                case R.id.Y40b2:
                    //SendMessage("suRoalDownPress");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x40;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y40b2",Toast.LENGTH_LONG);
                    break;
                case R.id.Y40a2:
                    //SendMessage("suRangeUp");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x20;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y40a2",Toast.LENGTH_LONG);
                    break;
                case R.id.Y28b2:
                    //SendMessage("suRangeDown");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x08;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y28b2",Toast.LENGTH_LONG);
                    break;
                case R.id.Y28a2:
                    //SendMessage("suRoalFloat");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x04;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y28a2",Toast.LENGTH_LONG);
                    break;
                case R.id.Y43b2:
                    //SendMessage("sujlLockChoose");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x02;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y43b2",Toast.LENGTH_LONG);
                    break;
                case R.id.Y43a2:
                    //SendMessage("suLockPress");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x01;
                    bytesSend[4]=(byte)0x00;
                    toast=Toast.makeText(getApplicationContext(),"Y43a2",Toast.LENGTH_LONG);
                    break;

                case R.id.Y42b2:
                    //SendMessage("suRoalDownPress");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x80;
                    toast=Toast.makeText(getApplicationContext(),"Y42b2",Toast.LENGTH_LONG);
                    break;
                case R.id.Y42a2:
                    //SendMessage("suRoalDownPress");
                    bytesSend[2]=(byte)0x00;
                    bytesSend[3]=(byte)0x00;
                    bytesSend[4]=(byte)0x40;
                    toast=Toast.makeText(getApplicationContext(),"Y42a2",Toast.LENGTH_LONG);
                    break;
            }
            bytesSend[5]=(byte)0x0d;
            bytesSend[6]=(byte)0x0a;
            SendHexMessage(bytesSend);
            showMyToast(toast,1000);
        }
    }
    private void bindID(){
        //卷扬速度 btnRoalSpeed1,btnRoalSpeed2,btnRoalSpeed3;
        btnRoalSpeed1 = (Button) findViewById(R.id.suspeed1);
        //btnsuARoalUP,btnsuARoalDown,btnsuAopen, btnsuAClose,btnsuALockOut,btnsuALockIn;
        //Y25a1,Y24b1,Y24a1,
        //超起A
        Y25a1 = (Button) findViewById(R.id.Y25a1);
        Y24b1 = (Button) findViewById(R.id.Y24b1);
        Y24a1 = (Button) findViewById(R.id.Y24a1);
        // Y39b1,Y39a1,Y40b1,
        Y39b1 = (Button) findViewById(R.id.Y39b1);
        Y39a1 = (Button) findViewById(R.id.Y39a1);
        Y40b1 = (Button) findViewById(R.id.Y40b1);
        // Y40a1,Y28b1,Y28a1,
        Y40a1 = (Button) findViewById(R.id.Y40a1);
        Y28b1 = (Button) findViewById(R.id.Y28b1);
        Y28a1 = (Button) findViewById(R.id.Y28a1);
        // Y43b1,Y43a1,Y42b1,Y42a1;
        Y43b1 = (Button) findViewById(R.id.Y43b1);
        Y43a1 = (Button) findViewById(R.id.Y43a1);
        Y42b1 = (Button) findViewById(R.id.Y42b1);
        Y42a1 = (Button) findViewById(R.id.Y42a1);
        //btnsuBRoalUP,btnsuBRoalDown,btnsuBopen,btnsuBClose,btnsuBLockOut,btnsuBLockIn;

        //超起B
        //Y25a2,Y24b2,Y24a2,
        Y25a2 = (Button) findViewById(R.id.Y25a2);
        Y24b2 = (Button) findViewById(R.id.Y24b2);
        Y24a2 = (Button) findViewById(R.id.Y24a2);
        // Y39b2,Y39a2,Y40b2,
        Y39b2 = (Button) findViewById(R.id.Y39b2);
        Y39a2 = (Button) findViewById(R.id.Y39a2);
        Y40b2 = (Button) findViewById(R.id.Y40b2);
        // Y40a2,Y28b2,Y28a2,
        Y40a2 = (Button) findViewById(R.id.Y40a2);
        Y28b2 = (Button) findViewById(R.id.Y28b2);
        Y28a2 = (Button) findViewById(R.id.Y28a2);
        // Y43b2,Y43a2,Y42b2,Y42a2;
        Y43b2 = (Button) findViewById(R.id.Y43b2);
        Y43a2 = (Button) findViewById(R.id.Y43a2);
        Y42b2 = (Button) findViewById(R.id.Y42b2);
        Y42a2 = (Button) findViewById(R.id.Y42a2);
        //超起其他动作
        //btnsuRangeUp,btnsuRangeDown,btnsuRoalFloat,  btnsuLockChoose,btnsuLockPress,btnsuRoalDownPress;
        // Y45b1,Y45a1,Y41a1,
        // Y29a1,Y50c1,Y44a1;
        Y45b1 = (Button) findViewById(R.id.Y45b1);
        Y45a1 = (Button) findViewById(R.id.Y45a1);
        Y41a1 = (Button) findViewById(R.id.Y41a1);

        Y29a1 = (Button) findViewById(R.id.Y29a1);
        Y50c1 = (Button) findViewById(R.id.Y50c1);
        Y44a1 = (Button) findViewById(R.id.Y44a1);

        textDemotest=(TextView)findViewById(R.id.textDemo);
    }

    private void bindListener(){
       // btnStartClient.setOnClickListener(myBtnClicker);
        //卷扬速度 btnRoalSpeed1,btnRoalSpeed2,btnRoalSpeed3;
        btnRoalSpeed1.setOnClickListener(myBtnClicker);
        //btnsuARoalUP,btnsuARoalDown,btnsuAopen, btnsuAClose,btnsuALockOut,btnsuALockIn;
        //超起A
        Y25a1.setOnClickListener(myBtnClicker);
        Y24b1.setOnClickListener(myBtnClicker);
        Y24a1.setOnClickListener(myBtnClicker);
        Y39b1.setOnClickListener(myBtnClicker);
        Y39a1.setOnClickListener(myBtnClicker);
        Y40b1.setOnClickListener(myBtnClicker);
        Y40a1.setOnClickListener(myBtnClicker);
        Y28b1.setOnClickListener(myBtnClicker);
        Y28a1.setOnClickListener(myBtnClicker);
        Y43b1.setOnClickListener(myBtnClicker);
        Y43a1.setOnClickListener(myBtnClicker);
        Y42b1.setOnClickListener(myBtnClicker);
        Y42a1.setOnClickListener(myBtnClicker);

        //btnsuBRoalUP,btnsuBRoalDown,btnsuBopen,btnsuBClose,btnsuBLockOut,btnsuBLockIn;
        //Y25a2,Y24b2,Y24a2,
        // Y39b2,Y39a2,Y40b2,

        //超起B
        Y25a2.setOnClickListener(myBtnClicker);
        Y24b2.setOnClickListener(myBtnClicker);
        Y24a2.setOnClickListener(myBtnClicker);

        Y39b2.setOnClickListener(myBtnClicker);
        Y39a2.setOnClickListener(myBtnClicker);
        Y40b2.setOnClickListener(myBtnClicker);
        // Y40a2,Y28b2,Y28a2,
        // Y43b2,Y43a2,Y42b2,Y42a2;
        Y40a2.setOnClickListener(myBtnClicker);
        Y28b2.setOnClickListener(myBtnClicker);
        Y28a2.setOnClickListener(myBtnClicker);

        Y43b2.setOnClickListener(myBtnClicker);
        Y43a2.setOnClickListener(myBtnClicker);
        Y42b2.setOnClickListener(myBtnClicker);
        Y42a2.setOnClickListener(myBtnClicker);
        //超起其他动作
        //btnsuRangeUp,btnsuRangeDown,btnsuRoalFloat,  btnsuLockChoose,btnsuLockPress,btnsuRoalDownPress;
        //Y45b1,Y45a1,Y41a1,
        // Y29a1,Y50c1,Y44a1;
        Y45b1.setOnClickListener(myBtnClicker);
        Y45a1.setOnClickListener(myBtnClicker);
        Y41a1.setOnClickListener(myBtnClicker);

        Y29a1.setOnClickListener(myBtnClicker);
        Y50c1.setOnClickListener(myBtnClicker);
        Y44a1.setOnClickListener(myBtnClicker);
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
    private void SendHexMessage(byte[] bytestosend)
    {
        try {
            final byte[] bb = bytestosend;
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    MainActivity.tcpClient.sendHex(bb);
                }
            });
        }
        catch (Exception e)
        {
            textDemotest.setText("连接失败！");
        }
    }
}
