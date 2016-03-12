package com.iotworkshop.keyless.pushme;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by wakir on 12/03/2016.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {


    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);


    }
}
