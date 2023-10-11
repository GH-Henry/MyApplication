package com.example.myapplication.closet_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.enums.EColor;
import com.example.myapplication.enums.EItemType;
import com.example.myapplication.structures.Closet;
import com.example.myapplication.structures.Item;

public class ClosetActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);
        recyclerView = findViewById(R.id.items_list);

        // TODO: Remove for deployment
        Closet closet = new Closet(null);
        closet.addItem(new Item(EItemType.T_SHIRT, EColor.BLACK));
        closet.addItem(new Item(EItemType.BLOUSE, EColor.WHITE));
        closet.addItem(new Item(EItemType.COAT, EColor.BROWN));
        closet.addItem(new Item(EItemType.DRESS, EColor.BEIGE));
        closet.addItem(new Item(EItemType.HEELS, EColor.GREEN));
        closet.addItem(new Item(EItemType.LONG_SLEEVE_SHIRT, EColor.GREY));
        closet.addItem(new Item(EItemType.LOAFERS, EColor.DARK_BLUE));

        ClosetItemAdapter adapter = new ClosetItemAdapter(closet);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}