package com.iotworkshop.keyless;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.google.android.gms.iid.InstanceID;
import com.greenfrvr.rubberloader.RubberLoaderView;
import com.iotworkshop.keyless.pushme.RegistrationIntentService;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private TextView tvDoorState;
    private Button bntDoorAction;


    public static boolean schermataAttiva = false;
//    private ProgressBar progressBar;
//    CircularProgressButton btnNeew;
    //TODO notifica push che aggiorna la schermata
    //


    /**
     *
     *
     Server API Key
     AIzaSyBrkSGKz1S4TLlGBQKgTOFb1A_DmKgJI7U

     Sender ID
     353747289989

     */

    //... GET status_door.json status : {opened, closed}
    //... GET toggle_door.json status : {opened, closed}

    public static String baseUrl = "https://keyless-iot-roadshow.herokuapp.com/events/";
    String status_url = baseUrl + "status_door.json?event[requester]=android";
    String inu;
    String toggle_url = baseUrl + "toggle_door.json?event[requester]=android";
    private JsonHttpResponseHandler handler;


    @Override
    protected void onPause() {
        super.onPause();
        schermataAttiva = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        schermataAttiva = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        schermataAttiva = true;

        setContentView(R.layout.activity_main);

        String authorizedEntity = "com.iotworkshop.keyless"; // Project id from Google Developer Console
        String scope = "GCM"; // e.g. communicating using GCM, but you can use any
        // URL-safe characters up to a maximum of 1000, or
        // you can also leave it blank.
//        startService(new Intent(this, RegistrationIntentService.class));


        bntDoorAction = (Button)findViewById(R.id.btnWithText);

//        btnNeew = (CircularProgressButton)findViewById(R.id.btnWithText);
        tvDoorState = (TextView)findViewById(R.id.tvDoorState);

//        progressBar = (ProgressBar)findViewById(R.id.progress);
        getIntent().getStringExtra("");
        IntentFilter filter = new IntentFilter("com.toxy.LOAD_URL");
        this.registerReceiver(new Receiver(), filter);

        handler = new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                if(!response.isNull("status")){

                    try {
                        manageStatus(response.getString("status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);

//                btnNeew.setProgress((int) (totalSize/bytesWritten));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                errorToast();
                bntDoorAction.setText("Error");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                errorToast();
                bntDoorAction.setText("Error");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                errorToast();
                bntDoorAction.setText("Error");

            }
        };

        bntDoorAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bntDoorAction.setText("Loading...");
                KeylessApplication.client.get(toggle_url,handler);
            }
        });

        bntDoorAction.setText("Loading...");
        KeylessApplication.client.get(status_url, handler);

    }


    private void manageStatus(String status){
        switch (status){
            case "opened":

                tvDoorState.setText("The door is opened");
                bntDoorAction.setText("CLOSE");
                bntDoorAction.setBackgroundColor(Color.RED);
                break;

            case "closed":

                tvDoorState.setText("The door is closed");
                bntDoorAction.setBackgroundColor(Color.GREEN);
                bntDoorAction.setText("OPEN");
                break;



        }
    }


    private void errorToast(){
        Toast.makeText(getApplicationContext(),"Error on connection with server",Toast.LENGTH_SHORT).show();
        bntDoorAction.setText("Error");

    }


    @Override
    protected void onResume() {
        super.onResume();
        schermataAttiva = true;
//        KeylessApplication.client.get(status_url, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//
//                if (!response.isNull("status")) {
//
//                    try {
//                        manageStatus(response.getString("status"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
//                errorToast();
//                bntDoorAction.setText("Error");
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//                errorToast();
//                bntDoorAction.setText("Error");
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//                errorToast();
//                bntDoorAction.setText("Error");
//
//            }
//
//
//
//        });
    }

    private class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            System.out.println("ON RECEIVED CALLED");
            arg1.getStringExtra("");
            bntDoorAction.setText("Loading...");
            KeylessApplication.client.get(status_url,handler);
        }
    }
}
