package com.example.myapplication.structures;

import android.media.Image;

import com.example.myapplication.enums.EColor;
import com.example.myapplication.enums.EItemType;

public class Item {
    public Image image;
    public EItemType type;
    public EColor color;

    public Item(EItemType type, EColor color) {
        this.type = type;
        this.color = color;

        // Search for the image of the item type of the particular color
        this.image = null;
    }
}
