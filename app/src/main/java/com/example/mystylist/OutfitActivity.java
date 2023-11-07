package com.example.mystylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.CheckBox;

import com.example.mystylist.closet_activity.ClosetActivity;
import com.example.mystylist.structures.Outfit;

import java.util.ArrayList;
import java.util.Arrays;

public class OutfitActivity extends AppCompatActivity {

    //OutfitLibrary outfits = new OutfitLibrary();
    //private Outfit[] outfitList = outfits.getOutfits();

    Outfit[] outfits = OutfitLibrary.getOutfits();

    ImageButton backButton;
    CheckBox casualCheckbox;
    CheckBox winterCheckbox;
    public Button filterButton;



    //get tags from user
    //pass them in below
    //ETag[] tagsList = ETag.tagsToMask();
    //return them and run in satisfy function
    //tagsSatisfyFilter
    //display what is satisfied

    //populate filteredOutfits

    private RecyclerView recyclerView;
    private OutfitItemAdapter adapter;

    public static ArrayList<Outfit> filteredOutfits = null; // Populate before creating adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfits);

        backButton = findViewById(R.id.back_button);
        casualCheckbox = (CheckBox)findViewById(R.id.casual_filter);
        winterCheckbox = (CheckBox)findViewById(R.id.winter_filter);
        filterButton = findViewById(R.id.filter_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(casualCheckbox.isChecked()){

                }

                if(winterCheckbox.isChecked()){

                }
            }
        });

        if (filteredOutfits == null)
            filteredOutfits = new ArrayList<>(Arrays.asList(OutfitLibrary.getOutfits()));

        recyclerView = findViewById(R.id.list_of_filtered);
        adapter = new OutfitItemAdapter(filteredOutfits);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }




}
