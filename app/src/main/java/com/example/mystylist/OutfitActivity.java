package com.example.mystylist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.CheckBox;

import com.example.mystylist.AccountActivity;
import com.example.mystylist.OutfitLibrary;
import com.example.mystylist.R;
import com.example.mystylist.enums.EColor;
import com.example.mystylist.enums.EItemType;
import com.example.mystylist.enums.ETag;
import com.example.mystylist.structures.Closet;
import com.example.mystylist.closet_activity.ClosetActivity;
import com.example.mystylist.structures.Item;
import com.example.mystylist.structures.Outfit;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class OutfitActivity extends AppCompatActivity {

    //OutfitLibrary outfits = new OutfitLibrary();
    //private Outfit[] outfitList = outfits.getOutfits();

    Outfit[] outfits = OutfitLibrary.getOutfits();

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

    public static ArrayList<Outfit> filteredOutfits; // Populate before creating adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfits);

        casualCheckbox = (CheckBox)findViewById(R.id.casual_filter);
        winterCheckbox = (CheckBox)findViewById(R.id.winter_filter);
        filterButton = findViewById(R.id.filter_button);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(casualCheckbox.isChecked()){

                }

                if(winterCheckbox.isChecked()){

                }
            }
        });


        recyclerView = findViewById(R.id.list_of_filtered);
        adapter = new OutfitItemAdapter(filteredOutfits);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }




}
