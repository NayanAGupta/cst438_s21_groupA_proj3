package com.gupta.cst438_s21_groupa_proj3;

public class ExampleItem {

    private String mImageURL;
    private String mText1;
    private String mText2;
    public ExampleItem(String imageURL, String text1, String text2) {
        mImageURL = imageURL;
        mText1 = text1;
        mText2 = text2;
    }
    public String getImageURL() {
        return mImageURL;
    }
    public String getText1() {
        return mText1;
    }
    public String getText2() {
        return mText2;
    }
}
