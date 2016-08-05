package com.six.achrist.client;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Annatina Christ on 18.07.2016.
 */
public class ConnectionService extends Service {


    public static final String SERVERIP = "192.168.56.1";
    public static final int PORTNUMBER = 8123;
    private static String result = null;
    Socket socket;
    Binder myBinder;





    @Override
    public void onCreate(){
        super.onCreate();

        Thread  backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //do the work in a seperate thread so the main thread does
                //not get bogged down
                Log.i("Thread " ,"Thread running");
                try {

                    connectSocket();
                    receiveResult();


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        backgroundThread.start();


    }

    //called when the service starts from a call to startService()
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Log.i("Service " , "Service Started by StartService()");
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Binder ", "onBind called..." );
        return myBinder;
    }

    public class MyLocalBinder extends Binder{

        ConnectionService getService(){
            return ConnectionService.this;
        }
    }



    public void connectSocket() throws IOException {

        socket = new Socket(SERVERIP,PORTNUMBER);
        Log.i("Connection ", "Connection established");

    }


    public String receiveResult() throws IOException {

        //open an input stream
        InputStream inStream = socket.getInputStream();
        //make a new input stream reader
        InputStreamReader inStreamReader = new InputStreamReader(inStream);
        //New Buffered Reader
        BufferedReader buffReader = new BufferedReader(inStreamReader);

        String srvMsg;

        srvMsg = buffReader.readLine();


        if (srvMsg.equals("1")){
            Log.i("Result" ,"Win");

        }else {
            Log.i("Result" , "Lose");


        }

        return srvMsg;
    }

    public  String getResult() throws IOException {
        if (result == null){
            result = receiveResult();

        }
        return result;
    }


    public class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                String result = getResult();
                if (result.equals("1")){
                    intent.putExtra("result","Win");

                }else {
                    intent.putExtra("result", "Lose");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            sendBroadcast(intent);

        }
    }

}





