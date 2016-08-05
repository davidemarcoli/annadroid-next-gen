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



        Intent intent = new Intent(this, ConnectionService.MyBroadcastReceiver.class);
        String result = intent.getStringExtra("result");
        TextView resultText = (TextView)findViewById(R.id.winTxt);
        resultText.setText(result);

        startService(new Intent(this,ConnectionService.class));

        doBindService();

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

    @Override
    public void onResume(){
        super.onResume();

        
    }


}

