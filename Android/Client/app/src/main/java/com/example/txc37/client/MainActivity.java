package com.example.txc37.client;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import static com.example.txc37.client.CodeHelper.getCode;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final String CODE = "com.example.txc37.client.code";
    ConnectionService cService;
    public boolean isBound;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

           //called when the connection with the service has been established
            //gives the service object (cService) so we can interact with the
            //service that is explicitly bound to a service, that is running
            //in our own process.
            Log.i("Connection", "Bound service connected");
           cService = ((ConnectionService.MyLocalBinder)iBinder).getService();




        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            //called if the service stops unexpectedly or the process crashes
            Log.i("Connection", "Error: bound service disconnected");
            cService = null;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submit_btn = (Button) findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(this);

        String code = getCode();

        TextView codeView = (TextView) findViewById(R.id.code);
        codeView.setText(code);

    }

    //bind this activity to the service
    public  void doBindService(){
        Toast.makeText(this,"Binding...", Toast.LENGTH_SHORT).show();
        if(!isBound){
            Intent bindIntent = new Intent(this,ConnectionService.class);
            isBound = bindService(bindIntent,mConnection,Context.BIND_AUTO_CREATE);

        }

    }

    //unbind this activity from the service
    public void doUnbindService(){
        Toast.makeText(this, "Unbinding...",Toast.LENGTH_SHORT).show();
        unbindService(mConnection);
        isBound = false;
    }






    @Override
    public void onClick(View v) {



        //Set up an intent to start the next activity
        Intent intent = new Intent(this, ShowMessageActivity.class);
        String code = getCode();
        intent.putExtra(CODE, code);
        this.startActivity(intent);
        startService(new Intent(this,ConnectionService.class));
        doBindService();



    }

    @Override
    public void onStart() {
        super.onStart();
        doBindService();


    }

    @Override
    public void onStop() {
        super.onStop();
        doUnbindService();


    }


}

