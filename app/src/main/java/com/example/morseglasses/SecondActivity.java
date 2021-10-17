package com.example.morseglasses;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.logging.Handler;
import java.util.logging.SocketHandler;

public class SecondActivity extends AppCompatActivity {
BluetoothAdapter mBTadapter;
    BluetoothSocket btSocket;
    StringBuilder sb= new StringBuilder();
    String Tag="Bluetooth devices";
Handler h;
    //serial port Bluetooth device
public static final UUID My_UUID =UUID.fromString("00001101-0000-1000-8000-00805F9B34F0");
public int Received_msg=1;
String s;
TextView dataTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_astivity);
        mBTadapter =BluetoothAdapter.getDefaultAdapter();
        dataTextView=(TextView) findViewById(R.id.dataTextView);
        //بيساعد الثريد انها تشتغل هيخليني اخزن كل بايت مع بعضها وبعدين اقوم عارضاها يعني بيجمع المسدج وبعدين يعرضها مرة واحدة عالليست فيو
         h=new Handler(){
            public void handlerMessage(android.os.Message msg){
                //بتتشيك في مسدج جاية ولا لا
                switch (msg.what){
                    case 1:
                        byte [] readBUF=(byte[]) msg.obj;
                        //بتحول المسدج من بايت لاسترنج
                        //الاوفسيت دي بداية المسدج
                        String strIncom=new String(readBUF,0,msg.arg1);
                        sb.append(strIncom);
                        //end_point
                        int endOfLine=sb.indexOf("#");
                        //substring
                        String sbprint=sb.substring(0,endOfLine);
                        //receive new data
                        sb.delete(0,sb.length());
s=s+""+sbprint;
                        dataTextView.setText(sbprint);

                }
            }
        };
        VerifyBluetooth();
    }


    private void VerifyBluetooth(){
        //Adapter - bluetooth
        mBTadapter =BluetoothAdapter.getDefaultAdapter();
        //if devices support bluetooth or not
        if(mBTadapter==null)
            Toast.makeText(getBaseContext(),"Your device don't support bluetooth",Toast.LENGTH_LONG).show();
        else
        {
            if(mBTadapter.isEnabled()){
                Log.d("Done","Bluetooth activated");
            }
            else{//intent
                Intent i=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                onActivityResult(RESULT_OK,1,i);
            }
    }
    }
    @Override
    protected void onResume(){
        super.onResume();
//address (port number)
       String Address= getIntent().getStringExtra("Extra_Address");
        BluetoothDevice device=mBTadapter.getRemoteDevice(Address);
        //socket
        try {
            btSocket=createBluetoothSocket(device);
            //delete search
            mBTadapter.cancelDiscovery();
            btSocket.connect();
            connectThread n=new connectThread(btSocket);
            n.start() ;
        } catch (IOException e) {
            try {
                //اقفل الكونيكشن لو فيه مشكلة
                btSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        //getmethod
        return device.createRfcommSocketToServiceRecord(My_UUID);
    }
public class connectThread extends Thread{
private InputStream mmInstream;
private OutputStream mmOutstream;
connectThread(BluetoothSocket socket){
    try {
        mmInstream= socket.getInputStream();
        mmOutstream= socket.getOutputStream();
    } catch (IOException e) {
        e.printStackTrace();
    }

}
@Override
//بقرا الداتا من البلوتوث
    public void run(){
    byte [] buffer=new byte[256];
    //بيرجع حجم المسدج اللي اتقرات
    int bytes;
    //read data from arduino to mobile
   while (true){
    try {
       bytes= mmInstream.read(buffer);
       //handler

       //text view
    } catch (IOException e) {
        e.printStackTrace();
    }}
}
    


}
    public void clear (View v){
        dataTextView.setText("");
    }
}
