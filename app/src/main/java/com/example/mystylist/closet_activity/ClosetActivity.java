package com.example.mystylist.closet_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.example.mystylist.R;
import com.example.mystylist.enums.EColor;
import com.example.mystylist.enums.EItemType;
import com.example.mystylist.structures.Closet;
import com.example.mystylist.structures.Item;

public class ClosetActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    public static Closet current_closet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);
        recyclerView = findViewById(R.id.items_list);
        recyclerView.setHasFixedSize(false);

        // TODO: Remove for deployment
        current_closet = new Closet(null);
        current_closet.addItem(new Item(EItemType.T_SHIRT, EColor.BLACK));
        current_closet.addItem(new Item(EItemType.BLOUSE, EColor.WHITE));
        current_closet.addItem(new Item(EItemType.COAT, EColor.BROWN));
        current_closet.addItem(new Item(EItemType.DRESS, EColor.BEIGE));
        current_closet.addItem(new Item(EItemType.HEELS, EColor.GREEN));
        current_closet.addItem(new Item(EItemType.LONG_SLEEVE_SHIRT, EColor.GREY));
        current_closet.addItem(new Item(EItemType.LOAFERS, EColor.DARK_BLUE));

        ClosetItemAdapter adapter = new ClosetItemAdapter(current_closet);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

                if (current_closet.addItem(0, item)) {
                    // Update recycler
                    recyclerView.getAdapter().notifyItemInserted(1);
                    // Close popup
                    popupWindow.dismiss();
                }
                else {
                    // TODO: Warn user invalid inputs
                }
            }
        });

        popupWindow.showAtLocation(recyclerView, Gravity.CENTER, 0, 0);
    }
}