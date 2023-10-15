package com.example.mystylist.structures;

import android.util.Log;

import com.example.mystylist.enums.EColor;
import com.example.mystylist.enums.EItemType;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class Closet implements Serializable {
    private final Profile parent;
    private ArrayList<Item> items;

    public Closet(Profile parent){
        this.parent = parent;
        this.items = new ArrayList<>();
    }

    public Profile getParent() {
        return this.parent;
    }

    public Item[] getItems() {
        return this.items.toArray(new Item[] {});
    }

    public int getItemCount() {
        return this.items.size();
    }

    public boolean inCloset(Item item) {
        return this.items.contains(item);
    }

    public boolean addItem(Item new_item) {
        // Check if item is already in closet
        if (this.items.contains(new_item))
            return false;
        // else, add item to the closet
        this.items.add(new_item);
        return true;
    }

    public boolean addItem(int index, Item new_item) {
        // Check if item is already in closet
        if (this.items.contains(new_item))
            return false;
        // else, add item to the closet
        this.items.add(index, new_item);
        return true;
    }

    public Item removeItem(Item item) {
        // TODO
        // Check if equivalent item is in teh closet
        // If so, remove it from items and return the item
        // else, return null
        Log.d("UNFINISHED_FUNCTION", "Use of unfinished function removeItem(Item) in Closet class");
        return null;
    }

    public Item removeItem(EItemType type, EColor color) {
        // TODO
        // Check if there is a matching item in the closet
        // if so, remove and return it
        // else, return null
        Log.d("UNFINISHED_FUNCTION", "Use of unfinished function removeItem(EItemType, EColor) in Closet class");
        return null;
    }

    public Item getItemAt(int index) {
        return this.items.get(index);
    }
}
