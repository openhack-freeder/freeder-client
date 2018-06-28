package com.example.jooyoung.freeder;

import android.graphics.drawable.Drawable;

public class ListItem {
    private Drawable img;
    private String Title;
    private String dday;

    public Drawable getImg() {
        return img;
    }

    public String getTitle() {
        return Title;
    }

    public String getDday() {
        return dday;
    }

    public void setDday(String dday) {
        this.dday = dday;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }
}
