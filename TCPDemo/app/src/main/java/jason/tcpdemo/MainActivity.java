package jason.tcpdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jason.tcpdemo.coms.TcpClient;
import jason.tcpdemo.funcs.FuncDITest;
import jason.tcpdemo.funcs.FuncTcpClient;
import jason.tcpdemo.funcs.FuncTcpServer;
import jason.tcpdemo.funcs.FuncTest;
import jason.tcpdemo.funcs.FuncDITest;
import jason.tcpdemo.funcs.FuncAItest;
import jason.tcpdemo.funcs.FuncWelcome;

public class MainActivity extends Activity {

    private RadioButton radioBtnServer, radioBtnClient;
    private Button btnFuncEnsure;
    private Button btntest, btnDItest, btnAItest;
    private Button btnConnect, btnDisConnect;
    private EditText editClientIp, editClientPort;
    private TextView txtShowFunc;
    private MyRadioButtonCheck myRadioButtonCheck = new MyRadioButtonCheck();
    private MyButtonClick myButtonClick = new MyButtonClick();
    public static TcpClient tcpClient = null;
    public static int nflag = 0;
    ExecutorService exec = Executors.newCachedThreadPool();

    private class MyRadioButtonCheck implements RadioButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()) {
                case R.id.radio_Server:
                    if (b) {
                        txtShowFunc.setText("你选则的功能是：服务器");
                    }
                    break;
                case R.id.radio_Client:
                    if (b) {
                        txtShowFunc.setText("你选则的功能是：客户端");

                    }
                    break;
            }
        }
    }

    private class MyButtonClick implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.btn_FunctionEnsure:

                    if (radioBtnServer.isChecked()) {
                        intent.setClass(MainActivity.this, FuncTcpServer.class);
                        startActivity(intent);
                    }
                    if (radioBtnClient.isChecked()) {
                        nflag = 1;
                        intent.setClass(MainActivity.this, FuncTcpClient.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.btn_Test: {
                    nflag = 2;
                    intent.setClass(MainActivity.this, FuncTest.class);
                    startActivity(intent);
                }
                break;
                case R.id.btn_DITest: {
                    nflag = 3;
                    intent.setClass(MainActivity.this, FuncDITest.class);
                    startActivity(intent);
                }
                break;
                case R.id.btn_AITest: {
                    nflag = 4;
                    intent.setClass(MainActivity.this, FuncAItest.class);
                    startActivity(intent);
                }
                break;
                //连接
                case R.id.btn_tcpClientConnect: {
                    tcpClient = new TcpClient(editClientIp.getText().toString(), getPort(editClientPort.getText().toString()));
                    exec.execute(tcpClient);

                    btnConnect.setEnabled(false);
                    btnDisConnect.setEnabled(true);
                    txtShowFunc.setText("服务器连接成功！");
                }
                break;
                //断开
                case R.id.btn_tcpClientDisConnect: {
                    tcpClient.closeSelf();
                    btnConnect.setEnabled(true);
                    btnDisConnect.setEnabled(false);
                    txtShowFunc.setText("服务器连接断开！");
                }
                break;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.welcomelayout);

        setContentView(R.layout.function);

        bindID();
        bindListener();
        Ini();

    }

    private int getPort(String msg) {
        if (msg.equals("")) {
            msg = "1234";
        }
        return Integer.parseInt(msg);
    }

    private void bindID() {
        radioBtnServer = (RadioButton) findViewById(R.id.radio_Server);
        radioBtnClient = (RadioButton) findViewById(R.id.radio_Client);
        btnFuncEnsure = (Button) findViewById(R.id.btn_FunctionEnsure);
        btntest = (Button) findViewById(R.id.btn_Test);
        btnDItest = (Button) findViewById(R.id.btn_DITest);
        btnAItest = (Button) findViewById(R.id.btn_AITest);
        // btnConnect,btnDisConnect
        btnConnect = (Button) findViewById(R.id.btn_tcpClientConnect);
        btnDisConnect = (Button) findViewById(R.id.btn_tcpClientDisConnect);
        txtShowFunc = (TextView) findViewById(R.id.txt_ShowFunction);
        //
        editClientIp = (EditText) findViewById(R.id.tcpClientIp);
        editClientPort = (EditText) findViewById(R.id.tcpClientPort);
    }

    private void bindListener() {
        radioBtnClient.setOnCheckedChangeListener(myRadioButtonCheck);
        radioBtnServer.setOnCheckedChangeListener(myRadioButtonCheck);
        btnFuncEnsure.setOnClickListener(myButtonClick);
        btntest.setOnClickListener(myButtonClick);
        btnDItest.setOnClickListener(myButtonClick);
        btnAItest.setOnClickListener(myButtonClick);

        btnConnect.setOnClickListener(myButtonClick);
        btnDisConnect.setOnClickListener(myButtonClick);
    }

    private void Ini() {
        btnDisConnect.setEnabled(false);
        // btnClientSend.setEnabled(false);
    }
}
