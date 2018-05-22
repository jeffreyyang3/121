package com.example.a121game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;


//class to be used for sending player info to dm
//Update DM does nothing now, but will be the button that sends data to the dm.

public class ChosenCharacter extends AppCompatActivity {
public Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_character);

        i= getIntent();


        TextView playerName = (TextView) findViewById(R.id.textView22);
        TextView characterName = (TextView) findViewById(R.id.textView23);

        EditText health = (EditText) findViewById(R.id.editText3);
        EditText intellect = (EditText) findViewById(R.id.editText4);
        EditText wisdom = (EditText) findViewById(R.id.editText5);
        EditText charisma = (EditText) findViewById(R.id.editText6);
        EditText constitution = (EditText) findViewById(R.id.editText13);
        EditText strength = (EditText) findViewById(R.id.editText14);
        EditText dexterity = (EditText) findViewById(R.id.editText16);


        String pName = i.getStringExtra("PlayerName");
        String cName = i.getStringExtra("CharacterName");
        String hp = i.getStringExtra("Health");

        String intel = i.getStringExtra("intellect");
        String wis = i.getStringExtra("wisdom");
        String charis = i.getStringExtra("charisma");
        String str = i.getStringExtra("strength");
        String dex = i.getStringExtra("dexterity");
        String cons = i.getStringExtra("constitution");

        playerName.setText(pName);
        characterName.setText(cName);
        health.setText(hp);

        intellect.setText(intel);
        wisdom.setText(wis);
        charisma.setText(charis);
        constitution.setText(cons);
        dexterity.setText(dex);
        strength.setText(str);



    }
}
