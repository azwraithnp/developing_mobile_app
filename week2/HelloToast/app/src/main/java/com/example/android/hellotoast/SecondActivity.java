package com.example.android.hellotoast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView countTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        countTextView = (TextView)findViewById(R.id.textView3);

        Intent i = getIntent();
        int mCount = i.getIntExtra("mCount", 0);
        countTextView.setText("" + mCount);
    }
}
