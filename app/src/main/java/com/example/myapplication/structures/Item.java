package com.example.myapplication.structures;

import android.media.Image;

import com.example.myapplication.enums.EColor;
import com.example.myapplication.enums.EItemType;

import java.io.Serializable;

public class Item implements Serializable {
    public int drawable_id;
    public EItemType type;
    public EColor color;

    public Item(EItemType type, EColor color) {
        this.type = type;
        this.color = color;

        // Search for the image of the item type of the particular color
        this.drawable_id = 0;
    }
}
