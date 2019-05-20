package com.example.fragmentexample2;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private boolean isFragmentDisplayed = false;
    static final String STATE_FRAGMENT = "state_of_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.open_button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFragmentDisplayed){
                    displayFragment();
                }
                else {
                    closeFragment();
                }
            }
        });

    if(savedInstanceState !=null){
        isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);
        if(isFragmentDisplayed){
            //if the fragment is displayed, change the button to close
            mButton.setText(R.string.close);
        }
    }
    }
    public void onSaveInstanceState(Bundle saveInstanceState) {
        saveInstanceState.putBoolean(STATE_FRAGMENT,isFragmentDisplayed);
        super.onSaveInstanceState(saveInstanceState);
    }

    public void displayFragment(){
        SimpleFragment simpleFragment = SimpleFragment.newInstance();
        //Get fragmentManager and start a transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Add the SimpleFragment
        fragmentTransaction.add(R.id.fragment_container,simpleFragment).addToBackStack(null).commit();
        //Update the button text
        mButton.setText(R.string.close);
        //set boolean flag to indicate fragment is open
        isFragmentDisplayed = true;
    }

    public void closeFragment(){
        //Get the fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Check to see if the fragment is already showing
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        if(simpleFragment != null){
            //Create and commit hte transaction to remove the fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        //update the button text
        mButton.setText(R.string.open);
        //set boolean value to indicate fragment is close
        isFragmentDisplayed = false;
    }

    public void btnNext(View view) {
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }
}
