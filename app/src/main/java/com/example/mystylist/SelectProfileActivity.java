package com.example.mystylist;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystylist.structures.Profile;
import com.google.android.material.snackbar.Snackbar;

import java.util.LinkedList;
import java.util.function.Function;

public class SelectProfileActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProfileAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);
        recyclerView = findViewById(R.id.profile_list);

        adapter = new ProfileAdapter(new LinkedList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        Database.getProfiles(LoginActivity.activeAccount.getUsername(), new Function<Profile, Void>() {
            @Override
            public Void apply(Profile profile) {
                adapter.addProfileAt(1, profile);
                return null;
            }
        });
    }

    public void showAddProfilePopup() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(R.layout.popup_add_profile, null);
        int width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        EditText profileName = popupView.findViewById(R.id.profile_name);
        Button cancelAddProfileButton = popupView.findViewById(R.id.cancel_add_Account_button);
        Button acceptAddProfileButton = popupView.findViewById(R.id.accept_add_Account_button);

        acceptAddProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = profileName.getText().toString();
                Profile profile = new Profile(name);

                if (!profile.hasValidName()) {
                    Snackbar snackbar = Snackbar.make(view, "Profile name cannot be empty.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else if (adapter.addProfileAt(1, profile) == null) {
                    Snackbar snackbar = Snackbar.make(view, "Profile already in account.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else {
                    Database.addProfile(LoginActivity.activeAccount.getUsername(), profile);
                    popupWindow.dismiss();
                }
            }
        });
        cancelAddProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(recyclerView, Gravity.CENTER, 0, 0);
    }

    public void selectProfile(Profile profile) {
        AccountActivity.selectProfile(profile);
        finish();
    }

}
