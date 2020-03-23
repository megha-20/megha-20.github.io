package com.example.trelloapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.trelloapp.Main.MainActivity;

public class TeamList extends AppCompatActivity {
    Button boardbtnone;
    //Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        boardbtnone = findViewById(R.id.board_btn2);
       // toolbar = findViewById(R.id.toolbartwo);

        boardbtnone.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeamList.this, MainActivity.class);
                startActivity(intent);
    //             toolbar.setTitle("ProjectOne");
            }

        });
    }}
