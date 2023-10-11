package com.example.mystylist.enums;

@SuppressWarnings("unused")
public enum EColor {
    PINK("Pink"),
    RED("Red"),
    ORANGE("Orange"),
    BEIGE("Beige"),
    YELLOW("Yellow"),
    GREEN("Green"),
    LIGHT_BLUE("Light Blue"),
    DARK_BLUE("Dark Blue"),
    PURPLE("Purple"),
    BROWN("Brown"),
    WHITE("White"),
    GREY("Grey"),
    BLACK("Black");

    public final String text;

    private EColor(String text) {
        this.text = text;
    }
}
