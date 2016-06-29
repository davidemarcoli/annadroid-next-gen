package com.example.txc37.helloworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Scene;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final String MESSAGE = "com.example.txc37.helloworld.message";
    //set the time the splash screen is displayed
    private final int SPLASH_DISPLAY_LENGTH = 5000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submit_btn = (Button) findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(this);




    }

    public String getMessage(){

        EditText message = (EditText) findViewById(R.id.message);

        String msg = message.getText().toString();
        return msg;



    }

    @Override
    public void onClick(View v) {

        new SendMessage().execute();



        Intent intent = new Intent(this, ShowMessageActivity.class);

        EditText editText = (EditText) findViewById(R.id.message);
        String message = editText.getText().toString();
        intent.putExtra(MESSAGE, message);

        this.startActivity(intent);






    }

    private class SendMessage extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {



            try {
            Socket socket = new Socket("192.168.56.1", 8123);
            System.out.println("Connected to server!");

            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message = getMessage();

            writer.write(message);
            System.out.println("The message you sent was: " +message);




            writer.flush();
            writer.close();
            in.close();
            socket.close();

        }catch (IOException e){
            e.printStackTrace();
        }


        return null;
        }
    }
}
