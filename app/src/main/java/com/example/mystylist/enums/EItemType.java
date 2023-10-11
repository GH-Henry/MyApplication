package com.example.mystylist.enums;

@SuppressWarnings("unused")
public enum EItemType {
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

    public final String text;

    EItemType(String text) {
        this.text = text;
    }
}
