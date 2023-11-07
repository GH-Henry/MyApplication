package com.example.mystylist.closet_activity;

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
import com.example.mystylist.R;
import com.example.mystylist.enums.EColor;
import com.example.mystylist.enums.EItemType;
import com.example.mystylist.structures.Closet;
import com.example.mystylist.structures.Item;
import com.example.mystylist.structures.Outfit;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class ClosetActivity extends AppCompatActivity {

    public static Closet closet = null;

    public ConstraintLayout layout;
    public ImageButton back_button;
    public Button clear_all_button;
    public Button search_outfits_button;
    public RecyclerView recyclerView;
    public ClosetItemAdapter adapter;

    public LinkedList<Item> selectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Outfit[] outfits;
                if (selectedItems.size() == 0)
                    outfits = OutfitLibrary.getOutfits();
                else
                    outfits = OutfitLibrary.getOutfitsContaining(selectedItems.toArray(new Item[]{}));

                Log.d("test", String.valueOf(outfits.length));

                Intent intent =  new Intent(ClosetActivity.this, OutfitActivity.class);
                OutfitActivity.filteredOutfits = new ArrayList<>(Arrays.asList(outfits));
                startActivity(intent);
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
                Item item = new Item(item_type, color);

                if (adapter.addItemAt(1, item) != null) {
                    popupWindow.dismiss();
                }
                else {
                    Snackbar snackbar = Snackbar.make(popupView, "Item already exists in closet.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
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
                adapter.notifyItemRangeRemoved(1, items.length);

                // Undo
                showSnackbar("Closet has been cleared.", "UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (Item i : items)
                            closet.addItem(i);
                        adapter.notifyItemRangeInserted(1, items.length);
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