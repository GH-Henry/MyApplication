package com.example.mystylist;

import com.example.mystylist.enums.EColor;
import com.example.mystylist.enums.EItemType;
import com.example.mystylist.enums.ETag;
import com.example.mystylist.structures.Item;
import com.example.mystylist.structures.Outfit;

import java.util.Collections;
import java.util.LinkedList;

public class OutfitLibrary {
    private static boolean initialized = false;
    private static Outfit[] outfits;

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
                            ETag.SEASON_SPRING, ETag.SEASON_SUMMER, ETag.SEASON_FALL, ETag.SEASON_WINTER,
                            ETag.WEATHER_FAIR, ETag.WEATHER_HOT,
                            ETag.STYLE_CASUAL,
                    }));



            outfits = tempOutfits.toArray(new Outfit[] {});
            initialized = true;
        }
    }

    public static boolean isInit() { return initialized; }
    public static Outfit[] getOutfits() {
        init();
        return outfits.clone();
    }
    public static Outfit[] getOutfitsContainingItems(Item[] items) {
        init();
        LinkedList<Outfit> filtered = new LinkedList<>();

        for (Outfit outfit : outfits) {
            for (Item item : items) {
                if (outfit.contains(item)) {
                    filtered.add(outfit);
                    break;
                }
            }
        }
        return filtered.toArray(new Outfit[] {});
    }

}
