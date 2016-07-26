package com.example.txc37.client;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final String CODE = "com.example.txc37.client.code";

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submit_btn = (Button) findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(this);

        String code = generateCode();

        TextView codeView = (TextView) findViewById(R.id.code);
        codeView.setText(code);
        startService(new Intent(this,ConnectionService.class));
        doBindService();
    }

    public  void doBindService(){
        bindService(new Intent(this,ConnectionService.ConnectSocket.class),mConnection, Context.BIND_AUTO_CREATE );
    }

    public String generateCode() {
        int randCode = (int) (Math.random() * 9000) + 1000;
        String codeString = String.valueOf(randCode);
        return codeString;

    }


    public String getCode() {

        TextView codeView = (TextView) findViewById(R.id.code);

        String code = codeView.getText().toString();
        return code;


    }

    @Override
    public void onClick(View v) {



        //Set up an intent to start the next activity
        Intent intent = new Intent(this, ShowMessageActivity.class);
        String code = getCode().toString();
        intent.putExtra(CODE, code);
        this.startActivity(intent);


    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

    }


}

