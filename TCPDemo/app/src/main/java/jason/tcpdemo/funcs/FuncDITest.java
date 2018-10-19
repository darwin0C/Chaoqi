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

import android.widget.ImageView;
import android.widget.TabHost;

import jason.tcpdemo.R;

import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import jason.tcpdemo.MainActivity;

/**
 * Created by Chao on 2018-03-15.
 */

public class FuncDITest extends Activity {
    private String TAG = "FuncDITest";
    private TextView textAtest;
    public static Context context;

    private ImageView ivs77ayes, ivs77ano, ivs78ayes, ivs78ano, ivs81ayes, ivs81ano, ivs82ayes, ivs82ano, ivs83ayes, ivs83ano;
    private ImageView ivs77byes, ivs77bno, ivs78byes, ivs78bno, ivs81byes, ivs81bno, ivs82byes, ivs82bno, ivs83byes, ivs83bno;
    private final MyHandler myHandler = new MyHandler(this);
    private MyBroadcastReceiver myBroadcastReceiver = new FuncDITest.MyBroadcastReceiver();
    ExecutorService exec = Executors.newCachedThreadPool();

    @SuppressLint("StaticFieldLeak")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ditestlayout);
        context = this;


        TabHost tab = (TabHost) findViewById(android.R.id.tabhost);

        //初始化TabHost容器
        tab.setup();

        //在TabHost创建标签，然后设置：标题／图标／标签页布局
        tab.addTab(tab.newTabSpec("tab1").setIndicator("左侧超起A", null).setContent(R.id.tab1));
        tab.addTab(tab.newTabSpec("tab2").setIndicator("右侧超起B", null).setContent(R.id.tab2));

        bindID();
        bindListener();
        //
        bindReceiver();
        imagehide();
    }

    private class MyHandler extends android.os.Handler {
        private WeakReference<FuncDITest> mActivity;

        MyHandler(FuncDITest activity) {
            mActivity = new WeakReference<FuncDITest>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity != null) {
                switch (msg.what) {
                    case 1:
                        //txtRcv.append(msg.obj.toString());
                        //textAtest.setText(msg.obj.toString());
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
            switch (mAction) {
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

    private void bindReceiver() {
        IntentFilter intentFilter = new IntentFilter("tcpClientReceiver");
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    private void bindID() {
        //卷扬速度 btnRoalSpeed1,btnRoalSpeed2,btnRoalSpeed3;
        //btnRoalSpeed1 = (Button) findViewById(R.id.suspeed1);
        // btnRoalSpeed2 = (Button) findViewById(R.id.suspeed2);
        //btnRoalSpeed3 = (Button) findViewById(R.id.suspeed3);
        textAtest = (TextView) findViewById(R.id.testaa);

        //ivs77ayes,ivs77ano,ivs78ayes,ivs78ano,ivs81ayes,ivs81ano,ivs82ayes,ivs82ano,ivs83ayes,ivs83ano;
        //超起A
        ivs77ayes = (ImageView) findViewById(R.id.s77ayes);
        ivs77ano = (ImageView) findViewById(R.id.s77ano);

        ivs78ayes = (ImageView) findViewById(R.id.s78ayes);
        ivs78ano = (ImageView) findViewById(R.id.s78ano);

        ivs81ayes = (ImageView) findViewById(R.id.s81ayes);
        ivs81ano = (ImageView) findViewById(R.id.s81ano);

        ivs82ayes = (ImageView) findViewById(R.id.s82ayes);
        ivs82ano = (ImageView) findViewById(R.id.s82ano);

        ivs83ayes = (ImageView) findViewById(R.id.s83ayes);
        ivs83ano = (ImageView) findViewById(R.id.s83ano);
        //超起B
        ivs77byes = (ImageView) findViewById(R.id.s77byes);
        ivs77bno = (ImageView) findViewById(R.id.s77bno);

        ivs78byes = (ImageView) findViewById(R.id.s78byes);
        ivs78bno = (ImageView) findViewById(R.id.s78bno);

        ivs81byes = (ImageView) findViewById(R.id.s81byes);
        ivs81bno = (ImageView) findViewById(R.id.s81bno);

        ivs82byes = (ImageView) findViewById(R.id.s82byes);
        ivs82bno = (ImageView) findViewById(R.id.s82bno);

        ivs83byes = (ImageView) findViewById(R.id.s83byes);
        ivs83bno = (ImageView) findViewById(R.id.s83bno);

    }

    private void bindListener() {
        // btnStartClient.setOnClickListener(myBtnClicker);
        //卷扬速度 btnRoalSpeed1,btnRoalSpeed2,btnRoalSpeed3;
        //btnRoalSpeed1.setOnClickListener(myBtnClicker);
        //btnRoalSpeed2.setOnClickListener(myBtnClicker);
        //btnRoalSpeed3.setOnClickListener(myBtnClicker);
        //btnsuARoalUP,btnsuARoalDown,btnsuAopen, btnsuAClose,btnsuALockOut,btnsuALockIn;
    }

    private void imagehide() {
        ivs77ayes.setVisibility(View.INVISIBLE);
        ivs78ayes.setVisibility(View.INVISIBLE);
        ivs81ayes.setVisibility(View.INVISIBLE);
        ivs82ayes.setVisibility(View.INVISIBLE);
        ivs83ayes.setVisibility(View.INVISIBLE);
        //
        ivs77byes.setVisibility(View.INVISIBLE);
        ivs78byes.setVisibility(View.INVISIBLE);
        ivs81byes.setVisibility(View.INVISIBLE);
        ivs82byes.setVisibility(View.INVISIBLE);
        ivs83byes.setVisibility(View.INVISIBLE);
    }

    private void processMessage(String messageRev) {
        String dest = "";
        if (messageRev != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(messageRev);
            dest = m.replaceAll("");
        }
        switch (dest) {
            //S77A
            case "77ay":
                ivs77ayes.setVisibility(View.VISIBLE);
                ivs77ano.setVisibility(View.INVISIBLE);
                break;
            case "77an":
                ivs77ayes.setVisibility(View.INVISIBLE);
                ivs77ano.setVisibility(View.VISIBLE);
                break;
            //S78A
            case "78ay":
                ivs78ayes.setVisibility(View.VISIBLE);
                ivs78ano.setVisibility(View.INVISIBLE);
                break;
            case "78an":
                ivs78ayes.setVisibility(View.INVISIBLE);
                ivs78ano.setVisibility(View.VISIBLE);
                break;
            //S81A
            case "81ay":
                ivs81ayes.setVisibility(View.VISIBLE);
                ivs81ano.setVisibility(View.INVISIBLE);
                break;
            case "81an":
                ivs81ayes.setVisibility(View.INVISIBLE);
                ivs81ano.setVisibility(View.VISIBLE);
                break;
            //S82A
            case "82ay":
                ivs82ayes.setVisibility(View.VISIBLE);
                ivs82ano.setVisibility(View.INVISIBLE);
                break;
            case "82an":
                ivs82ayes.setVisibility(View.INVISIBLE);
                ivs82ano.setVisibility(View.VISIBLE);
                break;
            //S83A
            case "83ay":
                ivs83ayes.setVisibility(View.VISIBLE);
                ivs83ano.setVisibility(View.INVISIBLE);
                break;
            case "83an":
                ivs83ayes.setVisibility(View.INVISIBLE);
                ivs83ano.setVisibility(View.VISIBLE);
                break;

            //超起B
            //S77B
            case "77by":
                ivs77byes.setVisibility(View.VISIBLE);
                ivs77bno.setVisibility(View.INVISIBLE);
                break;
            case "77bn":
                ivs77byes.setVisibility(View.INVISIBLE);
                ivs77bno.setVisibility(View.VISIBLE);
                break;
            //S78B
            case "78by":
                ivs78byes.setVisibility(View.VISIBLE);
                ivs78bno.setVisibility(View.INVISIBLE);
                break;
            case "78bn":
                ivs78byes.setVisibility(View.INVISIBLE);
                ivs78bno.setVisibility(View.VISIBLE);
                break;
            //S81B
            case "81by":
                ivs81byes.setVisibility(View.VISIBLE);
                ivs81bno.setVisibility(View.INVISIBLE);
                break;
            case "81bn":
                ivs81byes.setVisibility(View.INVISIBLE);
                ivs81bno.setVisibility(View.VISIBLE);
                break;
            //S82B
            case "82by":
                ivs82byes.setVisibility(View.VISIBLE);
                ivs82bno.setVisibility(View.INVISIBLE);
                break;
            case "82bn":
                ivs82byes.setVisibility(View.INVISIBLE);
                ivs82bno.setVisibility(View.VISIBLE);
                break;
            //S83B
            case "83by":
                ivs83byes.setVisibility(View.VISIBLE);
                ivs83bno.setVisibility(View.INVISIBLE);
                break;
            case "83bn":
                ivs83byes.setVisibility(View.INVISIBLE);
                ivs83bno.setVisibility(View.VISIBLE);
                break;
        }

    }
}
