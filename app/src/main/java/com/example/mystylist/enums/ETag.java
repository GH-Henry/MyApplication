package com.example.mystylist.enums;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public enum ETag {

    // Flag Distribution: 0b 00000000 00000000 00000000 00000000 00444444 00003333 00002222 00000111
    // 1 = Gender
    // 2 = Season
    // 3 = Weather
    // 4 = Style

    GENDER_NEUTRAL(  "Neutral",   0b001),
    GENDER_MASCULINE("Masculine", 0b010),
    GENDER_FEMININE( "Feminine",  0b100),

    SEASON_SPRING("Spring", 0b0001 << 8),
    SEASON_SUMMER("Summer", 0b0010 << 8),
    SEASON_FALL(  "Fall",   0b0100 << 8),
    SEASON_WINTER("Winter", 0b1000 << 8),

    WEATHER_FAIR( "Fair",    0b0001 << 16),
    WEATHER_HOT(  "Hot",     0b0010 << 16),
    WEATHER_COLD( "Cold",    0b0100 << 16),
    WEATHER_RAINY("Rainy",   0b1000 << 16),

    STYLE_CASUAL(               "Casual",                0b000001 << 24),
    STYLE_SMART_CASUAL(         "Smart Casual",          0b000010 << 24),
    STYLE_BUSINESS_CASUAL(      "Business Casual",       0b000100 << 24),
    STYLE_BUSINESS_PROFESSIONAL("Business Professional", 0b001000 << 24),
    STYLE_SEMI_FORMAL(          "Semi-formal",           0b010000 << 24),
    STYLE_FORMAL(               "Formal",                0b100000 << 24);

    public static final long EVERYTHING_MASK = 0xffffffffffffffffL;
    public static final long GENDER_CATEGORY_MASK = 0b111;
    public static final long SEASON_CATEGORY_MASK = 0b1111 << 8;
    public static final long WEATHER_CATEGORY_MASK = 0b1111 << 16;
    public static final long STYLE_CATEGORY_MASK = 0b111111 << 24;

    public final String text;
    public final long mask;

    ETag(String text, long mask) {
        this.text = text;
        this.mask = mask;
    }

    public static boolean hasMatch(long mask1, long mask2) {
        return (mask1 & mask2) != 0;
    }

    public static ETag[] maskToTags(long flags) {
        ArrayList<ETag> tags = new ArrayList<>();
        for (ETag tag : values()) {
            if (hasMatch(tag.mask, flags))
                tags.add(tag);
        }
        return tags.toArray(new ETag[] {});
    }
    public static long tagsToMask(ETag[] tags) {
        long flags = 0;
        for (ETag tag : tags)
            flags |= tag.mask;
        return flags;
    }

    @NonNull
    @Override
    public String toString() { return text; }
}
