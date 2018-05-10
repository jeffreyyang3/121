package com.example.a121game;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.nearby.connection.ConnectionsClient;

public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        //CODE EXECUTED ONLY ON CREATION OF THIS ACTIVITY
        //REASONING FOR THIS: BACK BUTTON ON ANDROID POPS ACTIVITIES OFF THE STACK AND
        //WE DON'T NEED TO CHECK FOR PERMISSIONS EVERY TIME
        //

        //if the user has not yet allowed the device to access location data, will prompt for permission
        //am currently unsure how we should handle declined permissions (probably exit app), as the connectivity api that will most likely be used
        //will require location data.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
        }



    }

    protected void onResume(){
        super.onResume();
        //
        //CODE THAT NEEDS TO BE CONTINUED ONCE AN ACTIVITY IS POPPED OFF THE STACK
        //
        Button ButtonCharacterSelect = findViewById(R.id.button_Character_Select);
        ButtonCharacterSelect.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){

                Intent i = new Intent(MainActivity.this, CharacterSelectActivity.class);
                startActivity(i);
            }
        });

        Button ButtonGroupSelect = findViewById(R.id.button_Group_Select);
        ButtonGroupSelect.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, GroupSelectActivity.class);
                startActivity(i);
            }
        });
    }


}
