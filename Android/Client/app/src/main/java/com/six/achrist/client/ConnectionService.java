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
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Annatina Christ on 18.07.2016.
 */
public class ConnectionService extends Service {


    public static final String SERVERIP = "192.168.56.1";
    public static final int PORTNUMBER = 8123;
    private final IBinder mBinder = new LocalBinder();
    Socket socket;

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */

    public class LocalBinder extends Binder{
        ConnectionService getService(){
            // Return this instance of LocalService so clients can call public methods
            return ConnectionService.this;
        }
    }

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
                    socketConnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        backgroundThread.start();
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void socketConnect() throws IOException {
        socket = new Socket(SERVERIP,PORTNUMBER);
        Log.i("Connection ", "Connection established");

    }

    public String getResult() throws IOException {


        InputStream inStream = socket.getInputStream();
        InputStreamReader inReader = new InputStreamReader(inStream);
        BufferedReader buffReader = new BufferedReader(inReader);

        String result;

        result = buffReader.readLine();

        return result;





    }
}





