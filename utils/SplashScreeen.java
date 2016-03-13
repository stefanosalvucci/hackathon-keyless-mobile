package com.iotworkshop.keyless.utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.iotworkshop.keyless.R;

/**
 * Created by wakir on 12/03/2016.
 */
public class SplashScreeen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.splash);


//        System.out.println("CIAO CIAO CIAO ");

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                //start your activity here
                startActivity(new Intent(getApplicationContext(), FakeList.class));
                finish();
            }

        }, 1000L);
    }
}
