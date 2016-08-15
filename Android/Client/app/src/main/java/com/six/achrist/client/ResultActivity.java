package com.six.achrist.client;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        TextView resultTxt = (TextView)findViewById(R.id.result_text);

        resultTxt.setText(result);

        Button tryAgain = (Button)findViewById(R.id.try_again);
        tryAgain.setOnClickListener(this);


        if (result.contains("next")) {
            tryAgain.setVisibility(View.VISIBLE);



        }else{
            tryAgain.setVisibility(View.GONE);

        }





    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}
