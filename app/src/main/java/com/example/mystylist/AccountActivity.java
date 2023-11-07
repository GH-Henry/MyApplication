package com.example.mystylist;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mystylist.closet_activity.ClosetActivity;
import com.example.mystylist.structures.ItemInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccountActivity extends AppCompatActivity {
    TextView AccountName;
    Button editAccountButton, outfitsButton, closetButton, favoritesButton;
    public static List<String> OutfitArr = new ArrayList<>();
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

        outfitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create and inflate the custom layout for the dialog
                View dialogView = getLayoutInflater().inflate(R.layout.popup_dropdowns, null);

                // Initialize the Spinners and the Button
                Spinner spinnerSeasons = dialogView.findViewById(R.id.spinnerSeasons);
                Spinner spinnerOccasion = dialogView.findViewById(R.id.spinnerOccasion);
                Button btnApplySelections = dialogView.findViewById(R.id.btnApplySelections);

                // Create ArrayAdapter for the first Spinner (Seasons)
                ArrayAdapter<CharSequence> seasonsAdapter = ArrayAdapter.createFromResource(
                        AccountActivity.this,
                        R.array.seasons_array,  // Create a string array resource with seasons
                        android.R.layout.simple_spinner_item
                );
                seasonsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSeasons.setAdapter(seasonsAdapter);

                // Create ArrayAdapter for the second Spinner (Occasions)
                ArrayAdapter<CharSequence> occasionsAdapter = ArrayAdapter.createFromResource(
                        AccountActivity.this,
                        R.array.occasions_array,  // Create a string array resource with occasions
                        android.R.layout.simple_spinner_item
                );
                occasionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerOccasion.setAdapter(occasionsAdapter);

                // Create the AlertDialog
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AccountActivity.this);
                dialogBuilder.setView(dialogView);
                final AlertDialog dialog = dialogBuilder.create();

                // Set a click listener for the "Apply Selections" button
                btnApplySelections.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle the selected values from the Spinners
                        String selectedSeason = spinnerSeasons.getSelectedItem().toString().trim();
                        String selectedGender = "Male";
                        String selectedOccasion = spinnerOccasion.getSelectedItem().toString();

                        Set<String> uniqueOutfitArr = new HashSet<>(); // Use a Set to store unique items

                        for (String s : ClosetActivity.itemList) {
                            ItemInfo itemInfo = ClosetActivity.clothingTypeToSeasonAndOccasion.get(s);
                            String season = itemInfo.getSeason();
                            String occasion = itemInfo.getOccasion();
                            String gender = itemInfo.getGender();
                            String clothingtype = itemInfo.getClothingType();

                            String[] seasons = season.split(",\\s*");
                            boolean seasonMatch = false;

                            for (String singleSeason : seasons) {
                                Log.d(selectedSeason, "Season from options");
                                if (singleSeason.trim().equalsIgnoreCase(selectedSeason)) {
                                    seasonMatch = true;
                                    break;
                                }
                            }

                            // Check if the item matches the selected season, occasion, and gender
                            if (seasonMatch && occasion.equalsIgnoreCase(selectedOccasion) && (gender.equalsIgnoreCase("Unisex") || gender.equalsIgnoreCase(selectedGender))) {
                                uniqueOutfitArr.add(s);
                            }
                        }

                        // Convert the Set to a List to maintain order
                        OutfitArr = new ArrayList<>(uniqueOutfitArr);

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AccountActivity.this);
                        View dialogView = getLayoutInflater().inflate(R.layout.item_outfit, null);
                        LinearLayout outfitContainer = dialogView.findViewById(R.id.outfitContainer); // The container in the popup layout

                        for (String outfitItem : OutfitArr) {
                            // Create a white box (you can customize this as needed)
                            TextView outfitTextView = new TextView(AccountActivity.this);
                            outfitTextView.setText(outfitItem);
                            outfitTextView.setBackgroundColor(Color.WHITE);

                            // Add the white box to the outfit container
                            outfitContainer.addView(outfitTextView);
                        }

                        dialogBuilder.setView(dialogView);

                        // Create and show the dialog
                        final AlertDialog outfitDialog = dialogBuilder.create();
                        outfitDialog.show();
                    }
                });

                // Show the dialog
                dialog.show();
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

    public void showAllUserData() {
        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");
        AccountName.setText(nameUser);
    }

    public void passUserData() {
        String userUsername = AccountName.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
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
