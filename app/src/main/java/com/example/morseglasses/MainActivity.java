package com.example.morseglasses;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
ListView paireddevicesList;
BluetoothAdapter mBTadapter;
String Tag="Bluetooth devices";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume(){
super.onResume();
//verify status bluetooth
        VerifyStatusBT();
        //ListView
        //define List View
        paireddevicesList=(ListView) findViewById(R.id.paireddevicesListView);

        //data
        mBTadapter =BluetoothAdapter.getDefaultAdapter();

        Set <BluetoothDevice> devices=mBTadapter.getBondedDevices();
        //Adapter
        ArrayAdapter<String>mpairedArrayAdapter=new ArrayAdapter<String>(this,R.layout.devicesbt);
        //Adapter - ListView
        paireddevicesList.setAdapter(mpairedArrayAdapter);

        for(BluetoothDevice x:devices){
            mpairedArrayAdapter.add(x.getName()+"\n"+x.getAddress());

        }

        //click on any bluetooth device - secondactivty (address);
        paireddevicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(MainActivity.this,SecondActivity.class);
                //address
                //17
                //info=name+address
                String info=((TextView) view).getText().toString();
                //subString
                String address=info.substring(info.length()-17);
                i.putExtra("Extra_Address",address);
                startActivity(i);
            }
        });
    }
    private void VerifyStatusBT(){
        //Adapter - bluetooth
        mBTadapter =BluetoothAdapter.getDefaultAdapter();
        //if devices support bluetooth or not
        if(mBTadapter==null)
            Toast.makeText(getBaseContext(),"Your device don't support bluetooth",Toast.LENGTH_LONG).show();
        else
        {
            if(mBTadapter.isEnabled()){
                Log.d(Tag,"Bluetooth activated");
            }
            else{//intent
                Intent i=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                onActivityResult(RESULT_OK,1,i);
            }
        }
    }
}