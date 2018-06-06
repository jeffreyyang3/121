package com.example.a121game;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlayerCreateActivity extends AppCompatActivity {
    public JSONArray jar;
    public JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_create2);

        getSupportActionBar().hide();

    }
    public void playerCreate(View view)
    {
        //getting ready to access info from edittext fields
        EditText playerName = (EditText) findViewById(R.id.editText);
        EditText characterName = (EditText) findViewById(R.id.editText2);
        EditText health = (EditText) findViewById(R.id.editText15);
        EditText intellect = (EditText) findViewById(R.id.editText7);
        EditText wisdom = (EditText) findViewById(R.id.editText8);
        EditText charisma = (EditText) findViewById(R.id.editText9);
        EditText constitution = (EditText) findViewById(R.id.editText12);
        EditText strength = (EditText) findViewById(R.id.editText10);
        EditText dexterity = (EditText) findViewById(R.id.editText11);

        //wont need this line probably, was going to do something different for writing to file
        //Intent detailIntent = new Intent(context, CharacterSelectActivity.class);

       // getting info from edittext fields
        String pName = playerName.getText().toString();
        String cName = characterName.getText().toString();
        int hp = Integer.parseInt(health.getText().toString());
        int inte = Integer.parseInt(intellect.getText().toString());
        int wis = Integer.parseInt(wisdom.getText().toString());
        int charis = Integer.parseInt(charisma.getText().toString());
        int cons = Integer.parseInt(constitution.getText().toString());
        int str = Integer.parseInt(strength.getText().toString());
        int dex = Integer.parseInt(dexterity.getText().toString());


        //creating stats array
        int[] stats = {inte, wis, charis, cons, str, dex};

        //creating new player object and getting its json representation
        //player playerObj = new player(pName, stats, cName, hp);

        json = new JSONObject();
        try {
            json.put("player", pName);
            json.put("character", cName);
            json.put("intellect", stats[0]);
            json.put("wisdom", stats[1]);
            json.put("charisma", stats[2]);
            json.put("strength", stats[3]);
            json.put("dexterity", stats[4]);
            json.put("constitution", stats[5]);

            json.put("health", hp);

        }catch(JSONException e){
            startActivity(new Intent(PlayerCreateActivity.this, CharacterSelectActivity.class));
        }

        //checking if other player objects already exist locally, if so will add to jsonarray and write back to file
        //if not will just create the file

        try {
            File f = new File(getFilesDir(), "file.ser");
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream o = new ObjectInputStream(fi);

            String j = (String)o.readObject();
            jar = new JSONArray(j);
            jar.put(jar.length(), json);
        }
        catch(Exception e)
        {
            File file = new File(getFilesDir(), "file.ser");
            try{
                file.createNewFile();

            }
            catch(Exception e2)
            {
                e.printStackTrace();
            }
        }

        //trying to write player object as json to file

        try
        {
            File f = new File(getFilesDir(), "file.ser");
            FileOutputStream fo = new FileOutputStream(f);
            ObjectOutputStream o = new ObjectOutputStream(fo);
            if(jar != null)
            {
                String j = jar.toString();
                o.writeObject(j);
            }
            else
            {
                jar = new JSONArray();
                jar.put(0, json);
                String j = jar.toString();
                o.writeObject(j);
            }
            o.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            startActivity(new Intent(PlayerCreateActivity.this, CharacterSelectActivity.class));
        }
        startActivity(new Intent(PlayerCreateActivity.this, CharacterSelectActivity.class));


    }
}
