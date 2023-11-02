package com.example.mystylist.structures;

import com.example.mystylist.enums.ETag;

import java.io.Serializable;

public class Outfit implements Serializable {
    /**
     * The name of the outfit.
     */
    private final String outfitName;
    /**
     * The description of the outfit.
     */
    private final String outfitDesc;
    /**
     * The items of the outfit.
     */
    private final Item[] items;
    /**
     * The tags of the outfit as a bit mask.
     */
    private final long tagFlags;  // Binary mask

    /**
     * Constructor.
     * @param outfitName the name of the outfit.
     * @param outfitDesc the description of the outfit.
     * @param items the items of the outfit.
     * @param tagFlags the tags of the outfit as a bit mask.
     */
    public Outfit(String outfitName, String outfitDesc, Item[] items, long tagFlags) {
        this.outfitName = outfitName;
        this.outfitDesc = outfitDesc;
        this.items = items;
        this.tagFlags = tagFlags;
    }

    /**
     * Constructor.
     * @param outfitName the name of the outfit.
     * @param outfitDesc the description of the outfit.
     * @param items the items of the outfit.
     * @param tags the tags of the outfit as an array of ETags.
     */
    public Outfit(String outfitName, String outfitDesc, Item[] items, ETag[] tags) {
        this.outfitName = outfitName;
        this.outfitDesc = outfitDesc;
        this.items = items;
        this.tagFlags = ETag.tagsToMask(tags);
    }

    /**
     * Returns the name of this outfit.
     * @return a String of this outfit's name.
     */
    public String getOutfitName() { return outfitName; }

    /**
     * Returns the description of this outfit.
     * @return a String of this outfit's description.
     */
    public String getOutfitDesc() { return outfitDesc; }

    /**
     * Returns the items in this outfit
     * @return an Item array of this outfit's items.
     */
    public Item[] getItems() { return items; }

    /**
     * Returns this outfit's tags as a bit mask.
     * @return a bit mask representing this outfit's outfit tags.
     */
    public long getTagFlags() { return tagFlags; }

    /**
     * Returns this outfit's tags as an ETag array.
     * @return an array of ETags representing this outfit's tags.
     */
    public ETag[] getTags() {
        return ETag.maskToTags(tagFlags);
    }

    /**
     * Returns whether this outfit satisfies the given mask.
     * @param filterMask the mask to check.
     * @return true, if this outfit satisfies the mask; else false.
     */
    public boolean tagsSatisfyFilter(long filterMask) {
        return (tagFlags & filterMask) == filterMask;
    }

    /**
     * Returns whether the given item is in this outfit.
     * @param item the item to check.
     * @return true, if the item is in the outfit; else false.
     */
    public boolean contains(Item item) {
        for (Item check : items) {
            if (item == check)
                return true;
        }
        return false;
    }

    /**
     * Returns whether this outfit contains all of the given items.
     * @param items the items to check for.
     * @return true, if all the items are in the outfit; else false.
     */
    public boolean contains(Item[] items) {
        for (Item item : items) {
            if (!contains(item))
                return false;
        }
        return true;
    }

    /**
     * Returns whether this outfit contains any of the given items.
     * @param items the items to check for.
     * @return true, if any of the items are in the outfit; else false.
     */
    public boolean containsAny(Item[] items) {
        for (Item item : items) {
            if (contains(item))
                return true;
        }
        return false;
    }
}
