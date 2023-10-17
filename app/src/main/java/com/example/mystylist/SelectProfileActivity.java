package com.example.mystylist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectProfileActivity extends AppCompatActivity {
    TextView selectProfile;
    Button user2Button, user3Button,addProfileButton, AccountName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);
        addProfileButton = findViewById(R.id.add_Account_button);
        user2Button = findViewById(R.id.user2_button);
        user3Button = findViewById(R.id.user3_button);
        AccountName = findViewById(R.id.AccountName);
        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(SelectProfileActivity.this, AddProfileActivity.class);
                    startActivity(intent);
                }
            });
        user2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectProfileActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
        user3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectProfileActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
        AccountName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectProfileActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
        }

}
