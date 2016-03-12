package com.iotworkshop.keyless.pushme;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.iotworkshop.keyless.KeylessApplication;
import com.iotworkshop.keyless.MainActivity;
import com.iotworkshop.keyless.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by wakir on 12/03/2016.
 */
public class RegistrationIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RegistrationIntentService(String name) {
        super(name);
    }

    public RegistrationIntentService(){
        super("name");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        String token = null;
        try {
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i("NOTIFICATION", "GCM Registration Token: " + token);


        } catch (IOException e) {
            e.printStackTrace();
        }
        // [END get_token]

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
//        Toast.makeText(getApplicationContext(),"Sending token to server: "+token,Toast.LENGTH_SHORT).show();
        KeylessApplication.client.get(MainActivity.baseUrl + "/register_token?event[token]=" + token, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


}
