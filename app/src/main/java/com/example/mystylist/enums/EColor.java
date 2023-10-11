package com.example.mystylist.enums;

@SuppressWarnings("unused")
public enum EColor {
    PINK("Pink", 0xff000000),
    RED("Red", 0xff000000),
    ORANGE("Orange", 0xff000000),
    BEIGE("Beige", 0xff000000),
    YELLOW("Yellow", 0xff000000),
    GREEN("Green", 0xff000000),
    LIGHT_BLUE("Light Blue", 0xff000000),
    DARK_BLUE("Dark Blue", 0xff000000),
    PURPLE("Purple", 0xff000000),
    BROWN("Brown", 0xff000000),
    WHITE("White", 0xff000000),
    GREY("Grey", 0xff000000),
    BLACK("Black", 0xff000000);

    public final String asText;
    public final int asInt;

    private EColor(String asText, int asInt) {
        this.asText = asText;
        this.asInt = asInt;
    }
}
