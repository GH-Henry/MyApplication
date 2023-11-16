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
import java.util.function.Function;

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

    private ArrayList<Outfit> filteredOutfits;

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

        filteredOutfits = new ArrayList<>();
        Database.requestOutfits(new receiveOutfitCallback());

        recyclerView = findViewById(R.id.list_of_filtered);
        adapter = new OutfitItemAdapter(filteredOutfits);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
    }

    private class receiveOutfitCallback implements Function<Outfit, Void> {
        @Override
        public Void apply(Outfit outfit) {
            OutfitActivity context = OutfitActivity.this;

            filteredOutfits.add(0, outfit);
            context.adapter.notifyItemInserted(0);
            Log.d("OutfitActivity", "Added outfit to list: " + outfit.toString());

            return null;
        }
    }


}
