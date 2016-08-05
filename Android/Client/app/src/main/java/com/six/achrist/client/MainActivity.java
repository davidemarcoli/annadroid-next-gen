package com.six.achrist.client;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity implements View.OnClickListener {


    ConnectionService cService;

    boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isBound){
            try {
                String result = cService.getResult();
                if (result.equals("1")){
                    Log.i("Result", "Win");
                }else {
                    Log.i("Result", "Lose");
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }

    @Override
    protected void onStart(){
        super.onStart();
        //bind to local Service
        Intent intent = new Intent(this,ConnectionService.class);
        bindService(intent,mConnection,Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop(){
        super.onStop();
        //Unbind from the service
        if (isBound){
            unbindService(mConnection);
            isBound = false;
        }
    }



    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder iBinder) {

           //called when the connection with the service has been established
            //gives the service object (cService) so we can interact with the
            //service that is explicitly bound to a service, that is running
            //in our own process.
            Log.i("Connection", "Bound service connected");
            ConnectionService.LocalBinder binder = (ConnectionService.LocalBinder) iBinder;
            cService = binder.getService();
            isBound = true;




        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            //called if the service stops unexpectedly or the process crashes
            Log.i("Connection", "Error: bound service disconnected");
            isBound = false;


        }
    };







    @Override
    public void onClick(View v) {




    }



}

