package com.example.a121game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HostList extends AppCompatActivity {
    JSONArray jArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_list);
        global_vars gv = (global_vars) getApplicationContext();
        jArray = gv.getJSONArray();
        JSONObject data = new JSONObject();
        int index = (int) getIntent().getExtras().get("position");
        try {
            data = jArray.getJSONObject(index);
            TextView playerName = (TextView) findViewById(R.id.PlayerName);
            playerName.setText(data.getString("PlayerName"));
            TextView health = (TextView) findViewById(R.id.Health);
            health.setText(data.getString("Health"));
            TextView intellect = (TextView) findViewById(R.id.intellect);
            intellect.setText(data.getString("intellect"));
            TextView dexterity = (TextView) findViewById(R.id.dexterity);
            dexterity.setText(data.getString("dexterity"));
            TextView constitution = (TextView) findViewById(R.id.constitution);
            constitution.setText(data.getString("constitution"));
            TextView charisma = (TextView) findViewById(R.id.charisma);
            charisma.setText(data.getString("charisma"));
            TextView strength = (TextView) findViewById(R.id.strength);
            strength.setText(data.getString("strength"));
        }
        catch(Exception e){
            e.printStackTrace();
        }







    }
}
