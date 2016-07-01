package com.example.txc37.client;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final String CODE = "com.example.txc37.client.code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submit_btn = (Button) findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(this);

        String code = generateCode();

        TextView codeView = (TextView) findViewById(R.id.code);
        codeView.setText(code);





    }

    public String generateCode(){
        int randCode = (int)(Math.random()*9000)+1000;
        String codeString = String.valueOf(randCode);
        return codeString;

    }



    public String getCode(){

        TextView codeView = (TextView) findViewById(R.id.code);

        String code = codeView.getText().toString();
        return code;



    }

    @Override
    public void onClick(View v) {

        //Execute the AsyncTask in the subclass to connect to the server
        new SendMessage().execute();

        //Set up an intent to start the next activity
        Intent intent = new Intent(this, ShowMessageActivity.class);
        String code = getCode().toString();
        intent.putExtra(CODE, code);
        this.startActivity(intent);





    }

    //subclass to execute the connection and the sending of the message
    protected class SendMessage extends AsyncTask<String, Void,String> {

        Context context;

        /*Method to establish a connection to the server
       and send the code that was generated
        */
        @Override
        protected String doInBackground(String... params) {


            //Variable for the port number
            int port = 8123;
            //IP we wanna connect to
            String ip =   "192.168.56.1";
            //initialise the socket
            Socket clientSocket;

            try {
                //connect to a socket that has that IP and Port number
                clientSocket = new Socket(ip, port);
                //Display a message
                System.out.println("Connected to server!");

                //Set up a new PrintWriter to write the code to the server
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());

                //This is where we put the code for sending it on
                String code = getCode();


                writer.write(code + "\n");
                writer.flush();
                System.out.println("Your code was: " +code);







            }catch (IOException e){
                e.printStackTrace();
            }



            return null;
        }


    }
}

