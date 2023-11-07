package com.example.mystylist.structures;

public class ItemInfo {
    private String season;
    private String occasion;
    private String gender;
    private String clothingType;

    public ItemInfo(String season, String occasion, String gender, String clothingType) {
        this.season = season;
        this.occasion = occasion;
        this.gender = gender;
        this.clothingType = clothingType;
    }

    public String getSeason() {
        return season;
    }

    public String getOccasion() {
        return occasion;
    }

    public String getGender() {
        return gender;
    }

    public String getClothingType() {
        return clothingType;
    }
}
