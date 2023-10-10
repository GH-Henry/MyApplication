package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectProfileActivity extends AppCompatActivity {
    Button user1Button, user2Button, user3Button,addProfileButton ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);
        user1Button = findViewById(R.id.user1_button);
        user2Button = findViewById(R.id.user2_button);
        user3Button = findViewById(R.id.user3_button);
        addProfileButton = findViewById(R.id.add_profile_button);
        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(SelectProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            });

    }

}
