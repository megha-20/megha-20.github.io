package com.example.trelloapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.trelloapp.Main.MainActivity;

public class list extends AppCompatActivity {

    Button boardbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        boardbtn = findViewById(R.id.board_btn);
        // toolbar = findViewById(R.id.toolbartwo);

        boardbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(list.this, MainActivity.class);
                startActivity(intent);

            }

        });
    }
}
