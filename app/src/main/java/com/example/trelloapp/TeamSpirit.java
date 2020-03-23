package com.example.trelloapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeamSpirit extends AppCompatActivity {

    Button boardbtnone;
   // Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_spirit);

        boardbtnone = findViewById(R.id.board_btn1);
       // toolbar = findViewById(R.id.toolbartwo);

        boardbtnone.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeamSpirit.this,TeamList.class);
                startActivity(intent);
             //   toolbar.setTitle("ProjectOne");

            }
        });

    }
}
