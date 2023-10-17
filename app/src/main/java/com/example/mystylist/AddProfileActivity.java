package com.example.mystylist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.mystylist.structures.Profile;



import androidx.appcompat.app.AppCompatActivity;

public class AddProfileActivity extends AppCompatActivity {
    public static Profile profile;
    EditText profileName;
    Button cancelAddProfileButton, acceptAddProfileButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        profileName = findViewById(R.id.profile_name);
        cancelAddProfileButton = findViewById(R.id.cancel_add_Account_button);
        acceptAddProfileButton = findViewById(R.id.accept_add_Account_button);
        acceptAddProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProfileActivity.this, SelectProfileActivity.class);
                startActivity(intent);
            }
        });
        cancelAddProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public Boolean validateName() {
        String val = profileName.getText().toString();
        if (val.isEmpty()) {
            profileName.setError("Name cannot be empty");
            return false;
        } else {
            profileName.setError(null);
            return true;
        }
    }
}
