package com.example.jooyoung.freeder;

import android.graphics.drawable.Drawable;

public class ListItem {
    private String Title;
    private String dday;
    private boolean favorite_check;

    public ListItem(String title,String dday){
        Title = title;
        this.dday = dday;
    }
    public ListItem(){

    }

    public String getTitle() {
        return Title;
    }

    public String getDday() {
        return dday;
    }

    public boolean isFavorite_check() {
        return favorite_check;
    }

    public void setDday(String dday) {
        this.dday = dday;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setFavorite_check(boolean favorite_check) {
        this.favorite_check = favorite_check;
    }
}
