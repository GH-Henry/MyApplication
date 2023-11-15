package com.example.mystylist.enums;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public enum EColor implements Serializable {
    PINK("Pink", 0xfff9e6e6),
    RED("Red", 0xffcf202a),
    ORANGE("Orange", 0xffff9900),
    BEIGE("Beige", 0xffc9ad93),
    YELLOW("Yellow", 0xffffd700),
    GREEN("Green", 0xff4e9b47),
    LIGHT_BLUE("Light Blue", 0xff8fd0ca),
    DARK_BLUE("Dark Blue", 0xff0f4c81),
    PURPLE("Purple", 0xff6f295b),
    BROWN("Brown", 0xff5d4a44),
    WHITE("White", 0xfff8f9fa),
    GREY("Grey", 0xff787470),
    BLACK("Black", 0xff333333);

    private static Map<String, EColor> fromStr;
    private static boolean fromStrInitialized = false;

    public final String asStr;
    public final int asInt;

    private EColor(String asText, int asInt) {
        this.asStr = asText;
        this.asInt = asInt;
    }

    @NonNull
    @Override
    public String toString() {
        return asStr;
    }

    public static EColor fromString(String str) {
        if (!fromStrInitialized)
            initFromStr();
        return fromStr.get(str);
    }

    private static void initFromStr() {
        fromStr = new HashMap<>();
        for (EColor color : EColor.values()) {
            fromStr.put(color.toString(), color);
        }
        fromStrInitialized = true;
    }
}
