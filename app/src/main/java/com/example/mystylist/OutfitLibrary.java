package com.example.mystylist;

import com.example.mystylist.enums.EColor;
import com.example.mystylist.enums.EItemType;
import com.example.mystylist.enums.ETag;
import com.example.mystylist.structures.Item;
import com.example.mystylist.structures.Outfit;

import java.util.LinkedList;

public class OutfitLibrary {
    /**
     * The state of initialization for the outfits array.
     */
    private static boolean initialized = false;
    /**
     * The outfits in the library.
     */
    private static Outfit[] outfits;

    /**
     * Initializes the outfits array for the library. Keeps track of initialized state, so should
     * call first every time the outfits array is used.
     */
    public static void init() {
        if (!initialized) {

            // Create outfits
            LinkedList<Outfit> tempOutfits = new LinkedList<>();

            // TODO Fill temp with 50 Outfits
            // Ex.
            tempOutfits.add(new Outfit(
                    "Simple Casual",
                    "A simple, casual outfit to wear at home or out with friends.",
                    new Item[] {
                            new Item(EItemType.T_SHIRT, EColor.BLACK),
                            new Item(EItemType.SHORTS, EColor.BEIGE),
                    },
                    new ETag[] {
                            ETag.GENDER_NEUTRAL,
                            ETag.SEASON_SPRING, ETag.SEASON_SUMMER, ETag.SEASON_FALL,
                            ETag.WEATHER_FAIR, ETag.WEATHER_HOT,
                            ETag.STYLE_CASUAL,
                    }));

            tempOutfits.add(new Outfit(
                    "Fall or Winter Smart Casual",
                    "A smart casual outfit to wear to work or out with friends.",
                    new Item[] {
                            new Item(EItemType.SWEATER, EColor.BLACK),
                            new Item(EItemType.JEANS, EColor.DARK_BLUE),
                            new Item(EItemType.JACKET, EColor.GREY),
                    },
                    new ETag[] {
                            ETag.GENDER_FEMININE,
                            ETag.SEASON_WINTER, ETag.SEASON_FALL,
                            ETag.WEATHER_FAIR, ETag.WEATHER_COLD,
                            ETag.STYLE_SMART_CASUAL,
                    }));



            outfits = tempOutfits.toArray(new Outfit[] {});
            initialized = true;
        }
    }

    /**
     * Returns all outfits in the library.
     * @return an array of outfits in the library.
     */
    public static Outfit[] getOutfits() {
        init();
        return outfits.clone();
    }

    /**
     * Returns the outfits in the library that contain the given item.
     * @param item the item to filter by.
     * @return an array of outfits that contain the item.
     */
    public static Outfit[] getOutfitsContaining(Item item) {
        init();
        LinkedList<Outfit> filtered = new LinkedList<>();

        for (Outfit outfit : outfits) {
            if (outfit.contains(item))
                filtered.add(outfit);
        }

        return filtered.toArray(new Outfit[]{});
    }

    /**
     * Returns the outfits in the library that contain all of the given items.
     * @param items the items to filter by.
     * @return an array of outfits that have all of the given items.
     */
    public static Outfit[] getOutfitsContaining(Item[] items) {
        init();
        LinkedList<Outfit> filtered = new LinkedList<>();

        for (Outfit outfit : outfits) {
            if (outfit.contains(items))
                filtered.add(outfit);
        }

        return filtered.toArray(new Outfit[] {});
    }

    /**
     * Returns the outfits in the library that contain at least one of the items given.
     * @param items the items to filter by.
     * @return an array of outfits that have at least one of the given items.
     */
    public static Outfit[] getOutfitsContainingAny(Item[] items) {
        init();
        LinkedList<Outfit> filtered = new LinkedList<>();

        for (Outfit outfit : outfits) {
            if (outfit.containsAny(items))
                filtered.add(outfit);
        }

        return filtered.toArray(new Outfit[] {});
    }

}
