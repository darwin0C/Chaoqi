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
    private TextView textDemotest;
    public static Context context;

    private ImageView ivs77ayes, ivs77ano, ivs78ayes, ivs78ano, ivs79ayes, ivs79ano, ivs80ayes, ivs80ano;
    private ImageView ivs77byes, ivs77bno, ivs78byes, ivs78bno, ivs79byes, ivs79bno, ivs80byes, ivs80bno;
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

        ivs79ayes = (ImageView) findViewById(R.id.s81ayes);
        ivs79ano = (ImageView) findViewById(R.id.s81ano);

        ivs80ayes = (ImageView) findViewById(R.id.s82ayes);
        ivs80ano = (ImageView) findViewById(R.id.s82ano);

        //超起B
        ivs77byes = (ImageView) findViewById(R.id.s77byes);
        ivs77bno = (ImageView) findViewById(R.id.s77bno);

        ivs78byes = (ImageView) findViewById(R.id.s78byes);
        ivs78bno = (ImageView) findViewById(R.id.s78bno);

        ivs79byes = (ImageView) findViewById(R.id.s81byes);
        ivs79bno = (ImageView) findViewById(R.id.s81bno);

        ivs80byes = (ImageView) findViewById(R.id.s82byes);
        ivs80bno = (ImageView) findViewById(R.id.s82bno);

        textDemotest=(TextView)findViewById(R.id.textDemo);

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
        ivs79ayes.setVisibility(View.INVISIBLE);
        ivs80ayes.setVisibility(View.INVISIBLE);
        //ivs83ayes.setVisibility(View.INVISIBLE);
        //
        ivs77byes.setVisibility(View.INVISIBLE);
        ivs78byes.setVisibility(View.INVISIBLE);
        ivs79byes.setVisibility(View.INVISIBLE);
        ivs80byes.setVisibility(View.INVISIBLE);
        //ivs83byes.setVisibility(View.INVISIBLE);
    }
    private String hex2Dex(String temp)
    {
        String binaryStr="";
        if(temp.length()==2)
        {
            binaryStr = toBinaryString(temp.substring(0,1));
        }
        return  binaryStr;
    }
    public static String toBinaryString(String hex) {
        String binary="";
        switch (hex)
        {
            case "0":
                binary="0000";
                break;
            case "1":
                binary="0001";
                break;
            case "2":
                binary="0010";
                break;
            case "3":
                binary="0011";
                break;
            case "4":
                binary="0100";
                break;
            case "5":
                binary="0101";
                break;
            case "6":
                binary="0110";
                break;
            case "7":
                binary="0111";
                break;
            case "8":
                binary="1000";
                break;
            case "9":
                binary="1001";
                break;
            case "A":
                binary="1010";
                break;
            case "B":
                binary="1011";
                break;
            case "C":
                binary="1100";
                break;
            case "D":
                binary="1101";
                break;
            case "E":
                binary="1110";
                break;
            case "F":
                binary="1111";
                break;
        }
        return binary;
    }
    private void processMessage(String messageRev) {
        String dest = "";
        if (messageRev != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(messageRev);
            dest = m.replaceAll("");

            int len=dest.length();
            if(len%2==0)
            {
                int n=len/2;
                String []str=new String[n];
                //String temp=dest;
                for(int i=0;i<n;i++)
                {
                    str[i]=dest.substring(2*i, 2*i+2);
                    //dest=temp;
                }
                if(n>19 /*&& str[0]=="FE"&& str[1]=="01"*/)
                {
                    String temp=hex2Dex(str[18]);
                    textDemotest.setText(temp);

                    char[] ar = temp.toCharArray();
                    if(ar[0]=='1')
                    {
                        ivs77ayes.setVisibility(View.VISIBLE);
                        ivs77ano.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        ivs77ayes.setVisibility(View.INVISIBLE);
                        ivs77ano.setVisibility(View.VISIBLE);
                    }

                    if(ar[1]=='1')
                    {
                        ivs78ayes.setVisibility(View.VISIBLE);
                        ivs78ano.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        ivs78ayes.setVisibility(View.INVISIBLE);
                        ivs78ano.setVisibility(View.VISIBLE);
                    }
                    if(ar[2]=='1')
                    {
                        ivs79ayes.setVisibility(View.VISIBLE);
                        ivs79ano.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        ivs79ayes.setVisibility(View.INVISIBLE);
                        ivs79ano.setVisibility(View.VISIBLE);
                    }
                    if(ar[3]=='1')
                    {
                        ivs80ayes.setVisibility(View.VISIBLE);
                        ivs80ano.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        ivs80ayes.setVisibility(View.INVISIBLE);
                        ivs80ano.setVisibility(View.VISIBLE);
                    }
                    //textlfPull.setText("    "+hex2Dex(str[2],str[3],str[4],str[5])+"    ");
                    //textrhPull.setText( "    "+hex2Dex(str[6],str[7],str[8],str[9])+"    ");
                    //textlockA.setText("    "+ hex2Dex(str[10],str[11],str[12],str[13])+"    ");
                    //textlockB.setText("    "+ hex2Dex(str[14],str[15],str[16],str[17])+"    ");
                }
            }
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
            case "79ay":
                ivs79ayes.setVisibility(View.VISIBLE);
                ivs79ano.setVisibility(View.INVISIBLE);
                break;
            case "79an":
                ivs79ayes.setVisibility(View.INVISIBLE);
                ivs79ano.setVisibility(View.VISIBLE);
                break;
            //S82A
            case "80ay":
                ivs80ayes.setVisibility(View.VISIBLE);
                ivs80ano.setVisibility(View.INVISIBLE);
                break;
            case "80an":
                ivs80ayes.setVisibility(View.INVISIBLE);
                ivs80ano.setVisibility(View.VISIBLE);
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
            case "79by":
                ivs79byes.setVisibility(View.VISIBLE);
                ivs79bno.setVisibility(View.INVISIBLE);
                break;
            case "79bn":
                ivs79byes.setVisibility(View.INVISIBLE);
                ivs79bno.setVisibility(View.VISIBLE);
                break;
            //S82B
            case "80by":
                ivs80byes.setVisibility(View.VISIBLE);
                ivs80bno.setVisibility(View.INVISIBLE);
                break;
            case "80bn":
                ivs80byes.setVisibility(View.INVISIBLE);
                ivs80bno.setVisibility(View.VISIBLE);
                break;
        }

    }
}
