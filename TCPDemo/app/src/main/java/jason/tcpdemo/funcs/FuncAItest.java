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

public class FuncAItest extends Activity {
    private String TAG = "FuncAITest";
    public static Context context;
    private TextView textDemotest;
    private TextView textlfPull, textrhPull, textlockA, textlockB;
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

    private class MyHandler extends android.os.Handler {
        private WeakReference<FuncAItest> mActivity;

        MyHandler(FuncAItest activity) {
            mActivity = new WeakReference<FuncAItest>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity != null) {
                switch (msg.what) {
                    case 1:
                        //txtRcv.append(msg.obj.toString());
                        //textlfPull.setText(msg.obj.toString());
                        String strShow = msg.obj.toString();
                        if (strShow.contains("2048656C6C6F"))
                            textDemotest.setText("连接成功！");
                        else
                            processMessage(strShow);
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
        //textlfPull,textrhPull,textlockA,textlockB,textRangeAngle,textLfAngle,textRhAngle;
        textlfPull = (TextView) findViewById(R.id.textleftpull);
        textrhPull = (TextView) findViewById(R.id.textrightpull);
        textlockA = (TextView) findViewById(R.id.textlockpressA);
        textlockB = (TextView) findViewById(R.id.textlockpressB);

        textDemotest = (TextView) findViewById(R.id.textDemo);

    }

    private void bindListener() {
        //btnRoalSpeed1.setOnClickListener(myBtnClicker);
    }

    private String hex2Dex(String temp, String temp2, String temp3, String temp4) {
        int minute = Integer.parseInt(temp, 16);
        int minute2 = Integer.parseInt(temp2, 16);
        int minute3 = Integer.parseInt(temp3, 16);
        int minute4 = Integer.parseInt(temp4, 16);
        return Integer.toString(minute * 1000 + minute2 * 100 + minute3 * 10 + minute4);
    }

    private void processMessage(String messageRev) {
        String dest = "";
        if (messageRev != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(messageRev);
            dest.toUpperCase();//转大写
            dest = m.replaceAll("");
            int len = dest.length();
            if (len % 2 == 0) {
                int n = len / 2;
                String[] str = new String[n];
                //String temp=dest;
                for (int i = 0; i < n; i++) {
                    str[i] = dest.substring(2 * i, 2 * i + 2);
                    //dest=temp;
                }
                if (n > 18 /*&& str[0]=="FE"&& str[1]=="01"*/) {
                    textlfPull.setText("    " + hex2Dex(str[2], str[3], str[4], str[5]) + "    ");
                    textrhPull.setText("    " + hex2Dex(str[6], str[7], str[8], str[9]) + "    ");
                    textlockA.setText("    " + hex2Dex(str[10], str[11], str[12], str[13]) + "    ");
                    textlockB.setText("    " + hex2Dex(str[14], str[15], str[16], str[17]) + "    ");
                }
            }
        }
    }
}
