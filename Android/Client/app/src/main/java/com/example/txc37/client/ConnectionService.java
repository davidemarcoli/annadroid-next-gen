package com.example.txc37.client;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by txc37 on 18.07.2016.
 */
public class ConnectionService extends Service {

    public static final String SERVERIP = "192.168.56.1";
    public static final int PORTNUMBER = 8123;
    InetAddress serverAddr;
    PrintWriter out;
    Socket socket;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        System.out.println("I am the on bind method");
        return myBinder;
    }

    private final IBinder myBinder = new LocalBinder();
    MainActivity mMainActivity = new MainActivity();

    public class LocalBinder extends Binder{
        public ConnectionService getService(){
            System.out.println("I am the local Binder");
            return ConnectionService.this;
        }
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    public void sendMessage(String message){

        if (out != null && !out.checkError()){
            System.out.println("Sending message " +message);
            out.write(message);
            out.flush();

        }



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        super.onStartCommand(intent,flags,startID);
        System.out.println("I am the on start method");

        Runnable connect = new ConnectSocket();
        new Thread(connect).start();
        return START_STICKY;

    }

    class ConnectSocket implements Runnable{

        @Override
        public void run(){


            try {
                serverAddr = InetAddress.getByName(SERVERIP);
                Log.e("TCP Client", "Connecting to Server");

                socket = new Socket(serverAddr, PORTNUMBER);

                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                Log.e("TCP Client", "Sent");
                Log.e("TCP Client", "Done");


            }catch(Exception e){
                e.printStackTrace();
        }
        }

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        try {
            socket.close();
            Log.e("Connection: ","Closed!");
        }catch (Exception e){
            e.printStackTrace();
        }
        socket = null;
    }


    }





