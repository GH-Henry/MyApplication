package com.example.mystylist.enums;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public enum EItemType implements Serializable {
    T_SHIRT("T-shirt"),
    SLEEVELESS_SHIRT("Sleeveless Shirt"),
    POLO("Polo"),
    LONG_SLEEVE_SHIRT("Long Sleeve Shirt"),
    BUTTON_DOWN_SHIRT("Button Down Shirt"),
    BLOUSE("Blouse"),
    PANTS("Pants"),
    SHORTS("Shorts"),
    JEANS("Jeans"),
    LONG_SKIRT("Long Skirt"),
    SHORT_SKIRT("Short Skirt"),
    SUIT_JACKET("Suit Jacket"),
    JACKET("Jacket"),
    COAT("Coat"),
    WINDBREAKER("Windbreaker"),
    SWEATER("Sweater"),
    HOODIE("Hoodie"),
    DRESS("Dress"),
    SUNDRESS("Sundress"),
    SPORTS_BRA("Sports Bra"),
    SHORT_SOCKS("Short Socks"),
    LONG_SOCKS("Long Socks"),
    LEGGINGS("Leggings"),
    SNEAKERS("Sneakers"),
    LOAFERS("Loafers"),
    DRESS_SHOES("Dress Shoes"),
    HEELS("Heels"),
    HIGH_HEELS("High Heels"),
    SANDALS("Sandals");

    private static Map<String, EItemType> fromStr;
    private static boolean fromStrInitialized = false;

    public final String asStr;

    EItemType(String asStr) {
        this.asStr = asStr;
    }

    @NonNull
    @Override
    public String toString() {
        return asStr;
    }

    public static EItemType fromString(String str) {
        if (!fromStrInitialized)
            initFromStr();
        return fromStr.get(str);

    }

    private static void initFromStr() {
        fromStr = new HashMap<>();
        for (EItemType item : EItemType.values()) {
            fromStr.put(item.toString(), item);
        }
        fromStrInitialized = true;
    }
}
