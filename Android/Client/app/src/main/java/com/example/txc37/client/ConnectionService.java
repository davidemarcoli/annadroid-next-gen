package com.example.txc37.client;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static com.example.txc37.client.CodeHelper.getCode;

/**
 * Created by txc37 on 18.07.2016.
 */
public class ConnectionService extends Service {

    public static final String SERVERIP = "192.168.56.1";
    public static final int PORTNUMBER = 8123;
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
                Log.i("Thread:" ,"Thread running");
                try {
                    connectSocket();
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
        Log.i("Service: " , "Service Started by StartService()");
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       Log.i("Binder: ", "onBind called..." );
        return myBinder;
    }

    public class MyLocalBinder extends Binder{

            ConnectionService getService(){
                return ConnectionService.this;
            }
    }



    public void connectSocket() throws IOException {

        socket = new Socket(SERVERIP,PORTNUMBER);
        Log.i("Connection: ", "Connection established");

        OutputStream outStream = socket.getOutputStream();
        OutputStreamWriter outWriter = new OutputStreamWriter(outStream);
        BufferedWriter buffWriter = new BufferedWriter(outWriter);

        String message = getCode();

        buffWriter.write(message);
        buffWriter.flush();
        buffWriter.close();


    }




}





