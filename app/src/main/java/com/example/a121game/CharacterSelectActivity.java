package com.example.a121game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class CharacterSelectActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);


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