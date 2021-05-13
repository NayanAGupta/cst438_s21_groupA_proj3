package com.gupta.cst438_s21_groupa_proj3;

public class FavExampleItem {

    private String FavImageURL;
    private String FavText1;
    private String FavText2;
    public FavExampleItem(String imageURL, String text1, String text2) {
        FavImageURL = imageURL;
        FavText1 = text1;
        FavText2 = text2;
    }
    public String getImageURL() {
        return FavImageURL;
    }
    public String getText1() {
        return FavText1;
    }
    public String getText2() {
        return FavText2;
    }
}
