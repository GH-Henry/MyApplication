package com.example.mystylist.enums;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public enum ETag {

    // Flag Distribution: 0b 00000000 00000000 00000000 00000000 00444444 00003333 00002222 00000111
    // 1 = Gender
    // 2 = Season
    // 3 = Weather
    // 4 = Style

    EVERYTHING("Everything", 0xffffffffffffffffL),  // 64 '1's

    GENDER_NEUTRAL("Neutral", 0b001),
    GENDER_MASCULINE("Masculine", 0b001),
    GENDER_FEMININE("Feminine", 0b001),
    GENDER_CATEGORY_MASK("Gender", 0b111),

    SEASON_SPRING("Spring", 0b0001 << 8),
    SEASON_SUMMER("Summer", 0b0010 << 8),
    SEASON_FALL("Fall", 0b0100 << 8),
    SEASON_WINTER("Winter", 0b1000 << 8),
    SEASON_CATEGORY("Season", 0b1111 << 8),

    WEATHER_FAIR("Fair", 0b0001 << 16),
    WEATHER_HOT("Hot", 0b0010 << 16),
    WEATHER_COLD("Cold", 0b0100 << 16),
    WEATHER_RAINY("Rainy", 0b1000 << 16),
    WEATHER_CATEGORY("Weather", 0b1111 << 16),

    STYLE_CASUAL("Casual", 0b000001 << 24),
    STYLE_SMART_CASUAL("Smart Casual", 0b000010 << 24),
    STYLE_BUSINESS_CASUAL("Business Casual", 0b000100 << 24),
    STYLE_BUSINESS_PROFESSIONAL("Business Professional", 0b001000 << 24),
    STYLE_SEMI_FORMAL("Semi-formal", 0b010000 << 24),
    STYLE_FORMAL("Formal", 0b100000 << 24),
    STYLE_CATEGORY("Style", 0b111111 << 24);

    public final String text;
    public final long mask;

    ETag(String text, long mask) {
        this.text = text;
        this.mask = mask;
    }

    public boolean hasMatch(ETag t) {
        return hasMatch(this.mask, t.mask);
    }
    public boolean hasMatch(long mask) {
        return hasMatch(this.mask, mask);
    }
    public static boolean hasMatch(ETag t1, ETag t2) {
        return hasMatch(t1.mask, t2.mask);
    }
    public static boolean hasMatch(ETag t, long mask) {
        return hasMatch(t.mask, mask);
    }
    public static boolean hasMatch(long mask1, long mask2) {
        return (mask1 & mask2) != 0;
    }

    public static boolean exclusiveMatch(long mask1, long mask2) {
        return mask1 == mask2;
    }

    public static ETag[] flagsToTags(long flags) {
        ArrayList<ETag> tags = new ArrayList<>();
        for (ETag tag : values()) {
            if (hasMatch(tag, flags))
                tags.add(tag);
        }
        return tags.toArray(new ETag[] {});
    }
    public static long tagsToFlags(ETag[] tags) {
        long flags = 0;
        for (ETag tag : tags)
            flags |= tag.mask;
        return flags;
    }

    @NonNull
    @Override
    public String toString() { return text; }
}
