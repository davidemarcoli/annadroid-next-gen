package com.example.txc37.helloworld;

import android.app.Activity;
import android.content.Context;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Scene;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final String MESSAGE = "com.example.txc37.helloworld.message";




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

        new SendMessage().execute();



        Intent intent = new Intent(this, ShowMessageActivity.class);

        TextView textView = (TextView) findViewById(R.id.code);
        String message = textView.getText().toString();
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

            String code = getCode();

            writer.write(code);
            System.out.println("Your code was: " +code);

             




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
