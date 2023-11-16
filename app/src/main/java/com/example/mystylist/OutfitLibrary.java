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
                    "Average Day",
                    "A simple, casual outfit to wear at home or out with friends.",
                    new Item[] {
                            new Item(EItemType.T_SHIRT, EColor.BLACK),
                            new Item(EItemType.SHORTS, EColor.BEIGE),
                            new Item(EItemType.SNEAKERS, EColor.BLACK),
                    },
                    new ETag[] {
                            ETag.GENDER_NEUTRAL,
                            ETag.SEASON_SPRING, ETag.SEASON_SUMMER, ETag.SEASON_FALL,
                            ETag.WEATHER_FAIR, ETag.WEATHER_HOT,
                            ETag.STYLE_CASUAL,
                    }));
            tempOutfits.add(new Outfit(
                    "Something Different",
                    "WRITE ME", // TODO
                    new Item[] {
                            // TODO
                            new Item(EItemType.T_SHIRT, EColor.BLACK),
                            new Item(EItemType.SHORTS, EColor.BEIGE),
                    },
                    new ETag[] {
                            // TODO
                    }));
            tempOutfits.add(new Outfit(
                    "Lounging at Home",
                    "WRITE ME", // TODO
                    new Item[] {
                            // TODO
                            new Item(EItemType.T_SHIRT, EColor.BLACK),
                            new Item(EItemType.SHORTS, EColor.BEIGE),
                    },
                    new ETag[] {
                            // TODO
                    }));
            tempOutfits.add(new Outfit(
                    "Fancy Dinner",
                    "WRITE ME", // TODO
                    new Item[] {
                            // TODO
                            new Item(EItemType.BLOUSE, EColor.WHITE),
                            new Item(EItemType.SHORTS, EColor.BEIGE),
                            new Item(EItemType.SHORTS, EColor.DARK_BLUE),
                    },
                    new ETag[] {
                            // TODO
                    }));
            tempOutfits.add(new Outfit(
                    "Family Party",
                    "WRITE ME", // TODO
                    new Item[] {
                            // TODO
                            new Item(EItemType.BLOUSE, EColor.WHITE),
                            new Item(EItemType.SHORTS, EColor.BEIGE),
                    },
                    new ETag[] {
                            // TODO
                    }));
            tempOutfits.add(new Outfit(
                    "Catch His Eye",
                    "WRITE ME", // TODO
                    new Item[] {
                            // TODO
                            new Item(EItemType.DRESS, EColor.BEIGE),
                            new Item(EItemType.SHORTS, EColor.BEIGE),
                    },
                    new ETag[] {
                            // TODO
                    }));
            tempOutfits.add(new Outfit(
                    "Sandy Outing",
                    "WRITE ME", // TODO
                    new Item[] {
                            // TODO
                            new Item(EItemType.DRESS, EColor.BEIGE),
                            new Item(EItemType.SHORTS, EColor.BEIGE),
                            new Item(EItemType.SHORTS, EColor.BEIGE),
                    },
                    new ETag[] {
                            // TODO
                    }));
            tempOutfits.add(new Outfit(
                    "Irish Fem Fatale",
                    "WRITE ME", // TODO
                    new Item[] {
                            // TODO
                            new Item(EItemType.HEELS, EColor.GREEN),
                            new Item(EItemType.SHORTS, EColor.BEIGE),
                            new Item(EItemType.SHORTS, EColor.BEIGE),
                            new Item(EItemType.SHORTS, EColor.BEIGE),
                    },
                    new ETag[] {
                            // TODO
                    }));
            tempOutfits.add(new Outfit(
                    "Business Day",
                    "WRITE ME", // TODO
                    new Item[] {
                            // TODO
                            new Item(EItemType.LONG_SLEEVE_SHIRT, EColor.GREY),
                            new Item(EItemType.SHORTS, EColor.BEIGE),
                            new Item(EItemType.LOAFERS, EColor.DARK_BLUE),
                    },
                    new ETag[] {
                            // TODO
                    }));
            tempOutfits.add(new Outfit(
                    "Flowery Day",
                    "WRITE ME", // TODO
                    new Item[] {
                            // TODO
                            new Item(EItemType.T_SHIRT, EColor.PINK),
                            new Item(EItemType.JEANS, EColor.DARK_BLUE),
                            new Item(EItemType.SNEAKERS, EColor.GREY),
                    },
                    new ETag[] {
                            // TODO
                    }));

            tempOutfits.add(new Outfit(
                    "Cold Day Outside",
                    "WRITE ME", // TODO
                    new Item[] {
                            new Item(EItemType.SWEATER, EColor.BLACK),
                            new Item(EItemType.JEANS, EColor.DARK_BLUE),
                            new Item(EItemType.JACKET, EColor.GREY),
                            new Item(EItemType.COAT, EColor.BROWN),
                    },
                    new ETag[] {
                            ETag.GENDER_FEMININE,
                            ETag.SEASON_WINTER, ETag.SEASON_FALL,
                            ETag.WEATHER_FAIR, ETag.WEATHER_COLD,
                            ETag.STYLE_SMART_CASUAL,
                    }));
            tempOutfits.add(new Outfit(
                    "Cold Day in the Forrest",
                    "WRITE ME", // TODO
                    new Item[] {
                            new Item(EItemType.SWEATER, EColor.BLACK),
                            new Item(EItemType.JEANS, EColor.DARK_BLUE),
                            new Item(EItemType.COAT, EColor.BROWN),
                    },
                    new ETag[] {
                            ETag.GENDER_FEMININE,
                            ETag.SEASON_WINTER, ETag.SEASON_FALL,
                            ETag.WEATHER_FAIR, ETag.WEATHER_COLD,
                            ETag.STYLE_SMART_CASUAL,
                    }));


            for (Outfit outfit : tempOutfits) {
                Database.addOutfit(outfit);
            }



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
            if (outfit.containsAll(items))
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
