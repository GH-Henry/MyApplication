package com.example.myapplication.closet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.enums.EColor;
import com.example.myapplication.enums.EItemType;
import com.example.myapplication.structures.Item;

public class ClosetActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);
        recyclerView = findViewById(R.id.items_list);

        Log.d("User", "Success");

        // Test Code
        Closet closet = new Closet(null);
        closet.addItem(new Item(EItemType.T_SHIRT, EColor.BLACK));
        closet.addItem(new Item(EItemType.BLOUSE, EColor.WHITE));
        closet.addItem(new Item(EItemType.COAT, EColor.BROWN));

        ClosetItemAdapter adapter = new ClosetItemAdapter(closet);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}