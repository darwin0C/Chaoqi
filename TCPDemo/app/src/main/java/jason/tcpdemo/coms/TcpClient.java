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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jason.tcpdemo.funcs.FuncRadioTest;
import jason.tcpdemo.funcs.FuncTcpClient;
import jason.tcpdemo.funcs.FuncTest;
import jason.tcpdemo.funcs.FuncDITest;
import jason.tcpdemo.funcs.FuncAItest;
import jason.tcpdemo.MainActivity;


public class TcpClient implements Runnable {
    private String TAG = "TcpClient";
    private String serverIP = "192.168.4.1";
    private int serverPort = 80;
    private PrintWriter pw;

    private DataInputStream dis;
    private boolean isRun = true;
    private Socket socket = null;
    byte buff[] = new byte[4096];
    private String rcvMsg;
    private int rcvLen;
    private BufferedReader reader = null;
    //static OutputStream mPrintWriterClient = null;
    static OutputStream socketWriter;
    static private InputStream socketReader;

    static private int timedelay=10;

    public TcpClient(String ip, int port) {
        this.serverIP = ip;
        this.serverPort = port;
    }

    public void closeSelf() {
        isRun = false;
    }

    public void send(String msg) {
        //byte buffsend[] = new byte[1024 * 10];
        //pw.println(strToHexString(msg));
        //pw.println(msg);
        try{
            pw.print(msg);
            pw.flush();
        }
       catch (Exception e)
       {
            String s=e.getMessage();
       }
    }

    /*
    * 发送十六进制
    * */
    public void sendHex(byte[] msg) {
        //byte buffsend[] = new byte[1024 * 10];
        //pw.println(strToHexString(msg));
        //pw.println(msg);
        try{
            byte []bb=new byte[]{ (byte)0xfe,(byte)0x31};
            //String srt2=new String(bb,"UTF-8");
            socketWriter.write(msg);
            socketWriter.flush();
        }
        catch (Exception e)
        {
            String s=e.getMessage();
        }
    }


    @Override
    public void run() {
        try {
            socket = new Socket(serverIP, serverPort);
            socket.setSoTimeout(5000);
            //pw = new PrintWriter(socket.getOutputStream(),true);
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
            socketWriter = socket.getOutputStream();
            socketReader = socket.getInputStream();
            //is = socket.getInputStream();
            //dis = new DataInputStream(is);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new MyRunnable()).start();
        while (isRun && socket != null && socket.isConnected()) {
            try {
                byte strRxBuf[] = new byte[1024];
                int len = socketReader.read(strRxBuf, 0, 1024);
                //if ((rcvMsg = reader.readLine()) != null)
                if (len> 0)
                {
                    byte[] bytesRev=new byte[len];
                    for(int index=0;index<len;index++)
                    {
                        bytesRev[index]=strRxBuf[index];
                    }
                    rcvMsg=bytes2HexString(bytesRev);
                    Log.i(TAG, "run: 收到消息:" + rcvMsg);
                    Intent intent = new Intent();
                    intent.setAction("tcpClientReceiver");
                    intent.putExtra("tcpClientReceiver", rcvMsg);
                    switch (MainActivity.nflag) {
                        case 1:
                            FuncTcpClient.context.sendBroadcast(intent);//将消息发送给主界面
                            timedelay=15;
                            break;
                        case 2:
                            FuncTest.context.sendBroadcast(intent);
                            timedelay=15;
                            break;
                        case 3:
                            FuncDITest.context.sendBroadcast(intent);
                            timedelay=2;
                            break;
                        case 4:
                            FuncAItest.context.sendBroadcast(intent);
                            timedelay=2;
                            break;
                        case 5:
                            FuncRadioTest.context.sendBroadcast(intent);
                            timedelay=15;
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
                socketWriter.close();
                socketReader.close();
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
                delay(1000 * timedelay);
                //send("connected...");
                byte[] bb=new byte[]{(byte)0xfe,(byte)0x01,(byte)0x0D,(byte)0x0A};
                sendHex(bb);
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
    /*
     * 字节数组转16进制字符串
     */
    public static String bytes2HexString(byte[] array) {
        StringBuilder builder = new StringBuilder();

        for (byte b : array) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            builder.append(hex);
        }
        return builder.toString().toUpperCase();
    }
}
