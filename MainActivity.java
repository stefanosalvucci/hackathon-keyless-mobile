package com.iotworkshop.keyless;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private ProgressBar progressBar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String authorizedEntity = "com.iotworkshop.keyless"; // Project id from Google Developer Console
        String scope = "GCM"; // e.g. communicating using GCM, but you can use any
        // URL-safe characters up to a maximum of 1000, or
        // you can also leave it blank.
//        startService(new Intent(this, RegistrationIntentService.class));


        bntDoorAction = (Button)findViewById(R.id.btnDoorAction);

        tvDoorState = (TextView)findViewById(R.id.tvDoorState);

        progressBar = (ProgressBar)findViewById(R.id.progress);

        final JsonHttpResponseHandler handler;

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


                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                errorToast();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                errorToast();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                errorToast();

            }
        };

        bntDoorAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeylessApplication.client.get(toggle_url,handler);
            }
        });

    }


    private void manageStatus(String status){
        switch (status){
            case "opened":

                tvDoorState.setText("The door is opened");
                bntDoorAction.setText("CLOSE");

                break;

            case "closed":

                tvDoorState.setText("The door is closed");
                bntDoorAction.setText("OPEN");
                break;



        }
    }


    private void errorToast(){
        Toast.makeText(getApplicationContext(),"Error on connection with server",Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        KeylessApplication.client.get(status_url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                if (!response.isNull("status")) {

                    try {
                        manageStatus(response.getString("status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                errorToast();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                errorToast();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                errorToast();

            }
        });
    }
}
