package com.reagan.shopIt.model.enums;

public enum CategoryType {
    CLOTHING_AND_APPARELS("Clothing and apparels"),
    FOOTWEAR_AND_SHOES("Footwear and Shoes"),
    ELECTRONICS_AND_GADGETS("Electronics and Gadgets"),
    GAMES_AND_TOYS("Games and Toys"),
    SPORTS_PRODUCTS("Sports Products"),
    FURNITURE("Furniture"),
    TOYS("Toys"),
    MEDICAL_SUPPLIES("Medical Supplies");

    private final String value;


    CategoryType(String value) {
        this.value = value;
    }
}
