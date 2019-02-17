package com.example.avinashmishra.helloconstraint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    int count = 0;
    TextView showCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showToast(View view) {
        Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT).show();
    }

    public void countUp(View view) {
        count++;

        showCount.setText("" + count);

        if(count % 2 == 0)
        {
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
        }
        else
        {
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    public void zeroClick(View view) {
        count = 0;

        showCount.setText("" + count);

        view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
    }
}
