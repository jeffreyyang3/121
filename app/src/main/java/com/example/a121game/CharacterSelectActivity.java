package com.example.a121game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class CharacterSelectActivity extends AppCompatActivity{
    private ListView mListView;
    public player playerChar;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);

        getSupportActionBar().hide();

        mListView = (ListView) findViewById(R.id.data_list_view);
        final ArrayList<player> list = new ArrayList<player>();

        try {
            File f = new File(getFilesDir(), "file.ser");
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream o = new ObjectInputStream(fi);

            String j = (String)o.readObject();
            JSONArray jar = new JSONArray(j);
            for (int i = 0; i < jar.length(); i++)
            {
                JSONObject row = jar.getJSONObject(i);


                try
                {
                    String playerName = row.getString("player");
                    String charName = row.getString("character");
                    int hp = Integer.parseInt(row.getString("health"));
                    playerChar = new player(playerName, charName, hp);
                    int intellect = Integer.parseInt(row.getString("intellect"));
                    int charisma = Integer.parseInt(row.getString("charisma"));
                    int wisdom = Integer.parseInt(row.getString("wisdom"));
                    int strength = Integer.parseInt(row.getString("strength"));
                    int dexterity = Integer.parseInt(row.getString("dexterity"));
                    int constitution = Integer.parseInt(row.getString("constitution"));
                    playerChar.stats( intellect, wisdom, charisma, strength, dexterity, constitution);

                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
                list.add(playerChar);
            }
        }
        catch(Exception e)
        {
           // empty.setVisibility(View.VISIBLE);
        }
        if(list.size() == 0)
        {
           // empty.setVisibility(View.VISIBLE);
        }

        // Create an array and assign each element to be the title
        // field of each of the ListData objects (from the array list)
        String[] listItems = new String[list.size()];

        for(int i = 0; i < list.size(); i++){
            player listD = list.get(i);
            listItems[i] = listD.CharacterName;
        }

        // Show the list view with the each list item an element from listItems
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);

        final Context context = this;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                player selected = list.get(position);

                // Create an Intent to reference our new activity, then call startActivity
                // to transition into the new Activity.
                Intent detailIntent = new Intent(context, ChosenCharacter.class);

                // pass some key value pairs to the next Activity (via the Intent)
                detailIntent.putExtra("id", position);
               // detailIntent.putExtra("id", position);
                detailIntent.putExtra("PlayerName", selected.PlayerName);
                detailIntent.putExtra("CharacterName", selected.CharacterName);
                detailIntent.putExtra("Health", Integer.toString(selected.Health));
                detailIntent.putExtra("intellect", Integer.toString(selected.PlayerStats[0]));
                detailIntent.putExtra("wisdom", Integer.toString(selected.PlayerStats[1]));
                detailIntent.putExtra("charisma", Integer.toString(selected.PlayerStats[2]));
                detailIntent.putExtra("strength", Integer.toString(selected.PlayerStats[0]));
                detailIntent.putExtra("dexterity", Integer.toString(selected.PlayerStats[0]));
                detailIntent.putExtra("constitution", Integer.toString(selected.PlayerStats[0]));

                startActivity(detailIntent);
            }
        });

        // Add character button
        Button add_character = findViewById(R.id.add_character);
        add_character.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(CharacterSelectActivity.this, PlayerCreateActivity.class);
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Add_Character:
                startActivity(new Intent(CharacterSelectActivity.this, PlayerCreateActivity.class));
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }
}