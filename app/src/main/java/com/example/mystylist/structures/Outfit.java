package com.example.mystylist.structures;

import com.example.mystylist.enums.ETag;

public class Outfit {
    private final String outfitName;
    private final String outfitDesc;
    private final Item[] items;
    private final long tagFlags;  // Binary mask

    public Outfit(String outfitName, String outfitDesc, Item[] items, long tagFlags) {
        this.outfitName = outfitName;
        this.outfitDesc = outfitDesc;
        this.items = items;
        this.tagFlags = tagFlags;
    }

    public String getOutfitName() { return outfitName; }
    public String getOutfitDesc() { return outfitDesc; }
    public Item[] getItems() { return items; }
    public long getTagFlags() { return tagFlags; }

    public ETag[] getTags() {
        return ETag.flagsToTags(tagFlags);
    }

    public boolean tagsSatisfyFilter(long filterMask) {
        return (tagFlags & filterMask) == filterMask;
    }
}
