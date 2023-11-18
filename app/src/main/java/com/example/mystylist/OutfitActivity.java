package com.example.mystylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.CheckBox;

import com.example.mystylist.structures.Item;
import com.example.mystylist.structures.Outfit;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class OutfitActivity extends AppCompatActivity {

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

    private ArrayList<Outfit> outfits;

    public static List<Outfit> givenOutfits = null;
    public static List<Item> filterItems = null;
    public static Long filterTags = null;

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

        // Decide how to load outfits
        outfits = new ArrayList<>();
        if (givenOutfits != null) {
            // Use the outfit(s) given to you.
            outfits = new ArrayList<>(givenOutfits);
        }
        else if (filterItems != null) {
            // Use the outfits from the database that match the items
            Database.requestOutfitsMatching(filterItems, new receiveOutfitCallback());
        }
        else if (filterTags != null) {
            // Use the outfits from the database that match the filter
            Database.requestOutfitsMatching(filterTags, new receiveOutfitCallback());
        }
        else {
            // Use every outfit from the database
            Database.requestOutfits(new receiveOutfitCallback());
        }



        recyclerView = findViewById(R.id.list_of_filtered);
        adapter = new OutfitItemAdapter(outfits);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
    }

    private class receiveOutfitCallback implements Function<Outfit, Void> {
        @Override
        public Void apply(Outfit outfit) {
            OutfitActivity context = OutfitActivity.this;

            outfits.add(0, outfit);
            context.adapter.notifyItemInserted(0);
            Log.d("OutfitActivity", "Added outfit to list: " + outfit.toString());

            return null;
        }
    }

    public void startDisplayOutfitFragment(Outfit outfit) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("outfit", outfit);

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.setReorderingAllowed(true);
        trans.add(R.id.fragmentContainerView, OutfitDisplayFragment.class, bundle);
        trans.commit();
    }

    public void stopDisplayOutfitFragment(Fragment fragment) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.remove(fragment);
        trans.commit();
    }

    @Override
    public void finish() {
        super.finish();
        givenOutfits = null;
        filterItems = null;
        filterTags = null;
    }


}
