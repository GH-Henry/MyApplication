package com.example.mystylist.structures;

import com.example.mystylist.R;
import com.example.mystylist.enums.EColor;
import com.example.mystylist.enums.EItemType;

import java.io.Serializable;

public class Item implements Serializable {
    public int drawable_id;
    public EItemType type;
    public EColor color;

    public Item(EItemType type, EColor color) {
        this.type = type;
        this.color = color;

        // Search for the image of the item type of the particular color
        this.drawable_id = R.drawable.ic_launcher_background;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Item))
            return false;

        final Item other = (Item) obj;
        if (other.type != this.type)
            return false;
        if (other.color != this.color)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 23;  // Arbitrary prime number
        int hash = 5;  // Arbitrary starting position

        hash = PRIME * hash + type.hashCode();
        hash = PRIME * hash + color.hashCode();

        return hash;
    }
}
