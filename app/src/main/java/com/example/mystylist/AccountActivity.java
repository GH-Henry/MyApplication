package com.example.mystylist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mystylist.closet_activity.ClosetActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
public class AccountActivity extends AppCompatActivity {
    TextView AccountName;
    Button editAccountButton, outfitsButton, closetButton, favoritesButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        AccountName = findViewById(R.id.AccountName);
        editAccountButton = findViewById(R.id.editButton);
        outfitsButton = findViewById(R.id.outfitsButton);
        closetButton = findViewById(R.id.closetButton);
        favoritesButton = findViewById(R.id.favoritesButton);
        showAllUserData();
        AccountName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, SelectProfileActivity.class);
                startActivity(intent);
            }
        });
        editAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, EditAccountActivity.class);
                passUserData();
                startActivity(intent);
            }
        });
        closetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Added for presentation
                Intent intent = new Intent(AccountActivity.this, ClosetActivity.class);
                startActivity(intent);
            }
        });
    }
    public void showAllUserData(){
        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");
        AccountName.setText(nameUser);
    }
    public void passUserData(){
        String userUsername = AccountName.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
                    String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                    String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
                    Intent intent = new Intent(AccountActivity.this, EditAccountActivity.class);
                    intent.putExtra("name", nameFromDB);
                    intent.putExtra("email", emailFromDB);
                    intent.putExtra("username", usernameFromDB);
                    intent.putExtra("password", passwordFromDB);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}