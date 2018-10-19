package jason.tcpdemo.coms;

import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jason.tcpdemo.funcs.FuncTcpClient;
import jason.tcpdemo.funcs.FuncTest;
import jason.tcpdemo.funcs.FuncDITest;
import jason.tcpdemo.funcs.FuncAItest;
import jason.tcpdemo.MainActivity;


public class TcpClient implements Runnable {
    private String TAG = "TcpClient";
    private String serverIP = "192.168.88.141";
    private int serverPort = 1234;
    private PrintWriter pw;
    private InputStream is;
    private DataInputStream dis;
    private boolean isRun = true;
    private Socket socket = null;
    byte buff[] = new byte[4096];
    private String rcvMsg;
    private int rcvLen;
    private BufferedReader reader = null;

    public TcpClient(String ip, int port) {
        this.serverIP = ip;
        this.serverPort = port;
    }

    public void closeSelf() {
        isRun = false;

    }

    public void send(String msg) {

        //byte buffsend[] = new byte[1024 * 10];
        //pw.println(msg);
        pw.println(strToHexString(msg));
        pw.flush();

    }

    private String strToHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    private String HexStingTOString(String strRev) {
        if (strRev == null || strRev.equals("")) {
            return "";
        }
        String dest = "";
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(strRev);
        dest = m.replaceAll("");
        byte[] baKeyword = new byte[dest.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(dest.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            dest = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return dest;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(serverIP, serverPort);
            socket.setSoTimeout(5000);
            //pw = new PrintWriter(socket.getOutputStream(),true);
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
            //is = socket.getInputStream();
            //dis = new DataInputStream(is);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new MyRunnable()).start();
        while (isRun && socket != null && socket.isConnected()) {

            try {
                if ((rcvMsg = reader.readLine()) != null) {
                    //rcvLen = dis.read(buff);
                    //rcvMsg = new String(buff,0,rcvLen,"utf-8");
                    rcvMsg=HexStingTOString(rcvMsg);
                    Log.i(TAG, "run: 收到消息:" + rcvMsg);
                    Intent intent = new Intent();
                    intent.setAction("tcpClientReceiver");
                    intent.putExtra("tcpClientReceiver", rcvMsg);
                    switch (MainActivity.nflag) {
                        case 1:
                            FuncTcpClient.context.sendBroadcast(intent);//将消息发送给主界面
                            break;
                        case 2:
                            FuncTest.context.sendBroadcast(intent);
                            break;
                        case 3:
                            FuncDITest.context.sendBroadcast(intent);
                            break;
                        case 4:
                            FuncAItest.context.sendBroadcast(intent);
                            break;
                    }
                }
                if (rcvMsg.equals("QuitClient")) {   //服务器要求客户端结束
                    isRun = false;
                    delay(200);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null)
            try {
                pw.close();
                //is.close();
                //dis.close();
                reader.close();
                socket.close();
                socket = null;

            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public class MyRunnable implements Runnable {
        @Override
        public void run() {
            while (isRun && socket != null && socket.isConnected()) {
                delay(1000 * 5);
                send("connected...");
            }
        }
    }

    private void delay(int msDelay) {
        try {
            Thread.currentThread();
            Thread.sleep(msDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
