package com.iotworkshop.keyless;

import android.app.Application;
import android.content.Intent;

import com.iotworkshop.keyless.pushme.RegistrationIntentService;
import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by wakir on 12/03/2016.
 */
public class KeylessApplication extends Application {



    public static AsyncHttpClient client = new AsyncHttpClient();


    @Override
    public void onCreate() {
        super.onCreate();

        startService(new Intent(this, RegistrationIntentService.class));




    }
}
