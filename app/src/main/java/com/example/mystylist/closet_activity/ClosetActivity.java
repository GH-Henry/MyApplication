package com.example.mystylist.closet_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.example.mystylist.AccountActivity;
import com.example.mystylist.OutfitActivity;
import com.example.mystylist.OutfitLibrary;
import com.example.mystylist.LoginActivity;
import com.example.mystylist.R;
import com.example.mystylist.enums.EColor;
import com.example.mystylist.enums.EItemType;
import com.example.mystylist.structures.Closet;
import com.example.mystylist.structures.Item;
import com.example.mystylist.structures.ItemInfo;
import com.example.mystylist.structures.Outfit;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class ClosetActivity extends AppCompatActivity {
    public static ArrayList<String> itemList = new ArrayList<>();

    public static Closet closet = null;

    public ConstraintLayout layout;
    public ImageButton back_button;
    public Button clear_all_button;
    public Button search_outfits_button;
    public RecyclerView recyclerView;
    public ClosetItemAdapter adapter;

    public LinkedList<Item> selectedItems;
    public static Map<String, ItemInfo> clothingTypeToSeasonAndOccasion = new HashMap<>();

    public void SeasonToItem() {

        clothingTypeToSeasonAndOccasion.put(EItemType.T_SHIRT.toString(), new ItemInfo("Spring , Summer", "Casual", "Unisex", "Top"));
        clothingTypeToSeasonAndOccasion.put(EItemType.SHORTS.toString(), new ItemInfo("Spring , Summer", "Casual", "Unisex", "Bottom"));
        clothingTypeToSeasonAndOccasion.put(EItemType.SLEEVELESS_SHIRT.toString(), new ItemInfo("Spring , Summer", "Casual", "Unisex", "Top"));
        clothingTypeToSeasonAndOccasion.put(EItemType.POLO.toString(), new ItemInfo("Spring , Summer", "Semi-Casual", "Unisex", "Top"));
        clothingTypeToSeasonAndOccasion.put(EItemType.LONG_SKIRT.toString(), new ItemInfo("Spring", "Casual", "Female", "Bottom"));
        clothingTypeToSeasonAndOccasion.put(EItemType.SHORT_SKIRT.toString(), new ItemInfo("Spring , Summer", "Casual", "Female", "Bottom"));
        clothingTypeToSeasonAndOccasion.put(EItemType.SHORT_SOCKS.toString(), new ItemInfo("Spring , Summer", "Casual", "Unisex", "Socks"));
        clothingTypeToSeasonAndOccasion.put(EItemType.LONG_SOCKS.toString(), new ItemInfo("Spring , Summer", "Casual", "Unisex", "Socks"));
        clothingTypeToSeasonAndOccasion.put(EItemType.LEGGINGS.toString(), new ItemInfo("Spring", "Casual", "Female", "Bottom"));
        clothingTypeToSeasonAndOccasion.put(EItemType.LOAFERS.toString(), new ItemInfo("Spring , Summer", "Casual", "Unisex", "Shoes"));
        clothingTypeToSeasonAndOccasion.put(EItemType.DRESS.toString(), new ItemInfo("Spring , Summer", "Casual", "Female", "Top"));
        clothingTypeToSeasonAndOccasion.put(EItemType.SANDALS.toString(), new ItemInfo("Spring , Summer", "Casual", "Unisex", "Shoes"));

        clothingTypeToSeasonAndOccasion.put(EItemType.SPORTS_BRA.toString(), new ItemInfo("Summer", "Sports", "Female", "Top"));
        clothingTypeToSeasonAndOccasion.put(EItemType.SNEAKERS.toString(), new ItemInfo("Spring , Summer", "Casual", "Unisex", "Shoes"));

// Fall Clothing Types
        clothingTypeToSeasonAndOccasion.put(EItemType.PANTS.toString(), new ItemInfo("Fall", "Casual", "Unisex", "Bottom"));
        clothingTypeToSeasonAndOccasion.put(EItemType.SWEATER.toString(), new ItemInfo("Fall , Winter", "Casual", "Unisex", "Top"));
        clothingTypeToSeasonAndOccasion.put(EItemType.HOODIE.toString(), new ItemInfo("Fall , Winter", "Casual", "Unisex", "Top"));
        clothingTypeToSeasonAndOccasion.put(EItemType.JEANS.toString(), new ItemInfo("Fall , Winter", "Casual", "Unisex", "Bottom"));
        clothingTypeToSeasonAndOccasion.put(EItemType.JACKET.toString(), new ItemInfo("Fall , Winter", "Casual", "Unisex", "Outerwear"));
        clothingTypeToSeasonAndOccasion.put(EItemType.WINDBREAKER.toString(), new ItemInfo("Fall , Winter", "Casual", "Unisex", "Outerwear"));
        clothingTypeToSeasonAndOccasion.put(EItemType.COAT.toString(), new ItemInfo("Fall , Winter", "Casual", "Unisex", "Outerwear"));
        clothingTypeToSeasonAndOccasion.put(EItemType.LONG_SLEEVE_SHIRT.toString(), new ItemInfo("Fall , Winter", "Casual", "Unisex", "Top"));
        clothingTypeToSeasonAndOccasion.put(EItemType.LONG_SOCKS.toString(), new ItemInfo("Fall , Winter", "Casual", "Unisex", "Socks"));

    }
    public Map<String, ItemInfo> getClothingTypeToSeasonAndOccasion() {
        return clothingTypeToSeasonAndOccasion;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SeasonToItem();
        loadClosetItemsFromFirebase();
        setContentView(R.layout.activity_closet);
        layout = findViewById(R.id.constraintLayout);

        // Setup layout
        back_button = findViewById(R.id.back_button);
        clear_all_button = findViewById(R.id.clear_all_button);
        search_outfits_button = findViewById(R.id.search_outfits_button);
        recyclerView = findViewById(R.id.items_list);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clear_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClearAllConfirmationPopup();
            }
        });

        search_outfits_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        recyclerView.setHasFixedSize(false);

        // TODO: For demo only. Remove for deployment
        if (closet == null) {
            closet = Closet.generateDemoCloset();
        }

        adapter = new ClosetItemAdapter(closet);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        selectedItems = new LinkedList<>();

        // Must be done at end
        enableSwipeToDelete();
    }

    private void loadClosetItemsFromFirebase() {

            String usernameFromLoginActivity = LoginActivity.username; // Assuming you have a valid username
            DatabaseReference closetItemsRef = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(usernameFromLoginActivity).child("closetItem");

            closetItemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        closet = new Closet(null);
                        itemList.clear(); // Clear the existing items

                        for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                            String itemType = itemSnapshot.child("itemType").getValue(String.class);
                            if (itemType != null) {
                                itemList.add(itemType);
                            }
                        }

                        // Now, your itemList is populated with data from Firebase.
                        // You can use it as needed.
                    } else {
                        // Handle the case when there is no data (snapshot doesn't exist)
                        // For example, you can display a message to the user.
                        Log.d("MyTag", "No data found in Firebase");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle any errors if necessary
                    Log.e("MyTag", "Firebase data loading error: " + error.getMessage());
                }
            });
        }



    public void showAddItemPopup() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(R.layout.popup_closet_add_item, null);
        int width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        Spinner type_spinner = popupView.findViewById(R.id.type_spinner);
        ArrayAdapter<EItemType> item_adapter = new ArrayAdapter<>(this, R.layout.spinner_row_clothing_type, EItemType.values());
        type_spinner.setAdapter(item_adapter);

        Spinner color_spinner = popupView.findViewById(R.id.color_spinner);
        ArrayAdapter<EColor> color_adapter = new ArrayAdapter<>(this, R.layout.spinner_row_clothing_color, EColor.values());
        color_spinner.setAdapter(color_adapter);

        Button button_cancel = popupView.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        Button button_accept = popupView.findViewById(R.id.button_accept);
        button_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EItemType item_type = EItemType.values()[type_spinner.getSelectedItemPosition()];
                EColor color = EColor.values()[color_spinner.getSelectedItemPosition()];
                ItemInfo itemInfo = clothingTypeToSeasonAndOccasion.get(item_type.toString());
                String season = itemInfo.getSeason();
                String occasion = itemInfo.getOccasion();
                String gender = itemInfo.getGender();
                String style = itemInfo.getClothingType();
                Log.d("MyTag", "item_type: " + item_type.toString());
                Log.d("MyTag", "color: " + color.toString());
                Log.d("MyTag", "itemInfo: " + itemInfo.toString());
                Log.d("MyTag", "season: " + season);
                Log.d("MyTag", "occasion: " + occasion);
                Log.d("MyTag", "gender: " + gender);
                itemList.add(item_type.toString());
                String usernameFromLoginActivity = LoginActivity.username;
                DatabaseReference closetItemsRef = FirebaseDatabase.getInstance().getReference().child("users").child(usernameFromLoginActivity).child("closetItem");
                String itemId = closetItemsRef.push().getKey(); // Generate a unique key

                // Create a map to store item attributes
                Map<String, Object> itemAttributes = new HashMap<>();
                itemAttributes.put("season", season);
                itemAttributes.put("occasion", occasion);
                itemAttributes.put("gender", gender);
                itemAttributes.put("itemType", item_type.toString());
                itemAttributes.put("color", color.toString());
                itemAttributes.put("style", style);

                // Set the item attributes in the database
                closetItemsRef.child(itemId).updateChildren(itemAttributes);
                Item item = new Item(item_type, color);
                // Add the item to your local adapter
                adapter.addItemAt(1, item);
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(recyclerView, Gravity.CENTER, 0, 0);
    }

    public void showClearAllConfirmationPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Clear All Items in Closet?");
        builder.setMessage("Would you like to clear all items in your closet?");
        builder.setPositiveButton("Clear Closet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Save items in case undo
                Item[] items = closet.getItems();
                closet.clearItems();
                adapter.notifyItemRangeRemoved(1, items.length); // Update the adapter

                String usernameFromLoginActivity = LoginActivity.username;

                // Remove all closet items for the user from the database
                DatabaseReference closetItemsRef = FirebaseDatabase.getInstance().getReference().child("users").child(usernameFromLoginActivity).child("closetItem");
                closetItemsRef.removeValue();

                // Undo
                showSnackbar("Closet has been cleared.", "UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Add the items back to the local adapter
                        for (Item i : items)
                            closet.addItem(i);
                        adapter.notifyItemRangeInserted(1, items.length); // Update the adapter

                        // Add the items back to the database
                        DatabaseReference userClosetItemRef = FirebaseDatabase.getInstance().getReference().child("users").child(usernameFromLoginActivity).child("closetItem");
                        for (Item item : items) {
                            String itemId = userClosetItemRef.push().getKey();
                            userClosetItemRef.child(itemId).setValue(item);
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void enableSwipeToDelete() {
        final float swipeThreshold = 0.7f;
        ClosetSwipeToDeleteCallback callback = new ClosetSwipeToDeleteCallback(this, swipeThreshold) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                final int position = viewHolder.getAdapterPosition();
                final Item item = adapter.removeItemAt(position);
                if (item == null)
                    return;  // Position not a closet item

                showSnackbar("Item was removed from the closet.", "UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.addItemAt(position, item);
                    }
                });
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void showSnackbar(String text, int duration) {
        Snackbar.make(layout, text, duration).show();
    }
    private void showSnackbar(String text) {
        showSnackbar(text, Snackbar.LENGTH_LONG);
    }
    private void showSnackbar(String text, String actionText, View.OnClickListener action, int duration) {
        Snackbar snackbar = Snackbar.make(layout, text, duration);
        snackbar.setAction(actionText, action);
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }
    private void showSnackbar(String text, String actionText, View.OnClickListener listener) {
        showSnackbar(text, actionText, listener, Snackbar.LENGTH_LONG);
    }

    @Override
    public void finish() {
        closet = null;
        super.finish();
    }
}