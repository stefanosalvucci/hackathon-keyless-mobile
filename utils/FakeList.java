package com.iotworkshop.keyless.utils;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.iotworkshop.keyless.MainActivity;
import com.iotworkshop.keyless.R;

/**
 * Created by wakir on 12/03/2016.
 */
public class FakeList extends AppCompatActivity{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Events list");
        setContentView(R.layout.lista);
        ListView listView;
        ArrayAdapter<String> arrayAdapter;
                (listView = (ListView) findViewById(R.id.listView)).setAdapter(arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                        new String[]{"Fitness gym (public)", "Termini Station (public)", "My Home (private)", "Intel Hackaton (public)",
                                "Codemotion (public)"}
                ) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View v = super.getView(position, convertView, parent);
                        ((TextView) v.findViewById(android.R.id.text1)).setTextColor(Color.BLACK);
                        return v;
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                new AlertDialog.Builder(FakeList.this)
                        .setTitle("Intel Hackaton")
                        .setMessage("You have been asigned locker #42 do you want to use it?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FakeList.this.startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();


            }
        });
    }
}
