package com.example.jooyoung.freeder;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;

public class EventInformation implements Serializable,Comparable<EventInformation> {
    private String event_name;
    private String event_time; // 날짜
    private String event_day; // 응모기간
    private String event_location;
    private String URL;
    private String event_genre;
    private boolean favorite;
    private Integer dday;

    public EventInformation(){
        favorite = false;
    }

    public EventInformation(String event_name,String event_day,String event_time,String event_location,String URL,String category){
        this.event_name = event_name;
        this.event_day = event_day;
        this.event_location = event_location;
        this.event_time = event_time;
        this.URL = URL;
        this.event_genre = category;
        favorite = true;
    }

    public String getEvent_day() {
        return event_day;
    }

    public String getEvent_location() {
        return event_location;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_time() {
        return event_time;
    }

    public String getURL() {
        return URL;
    }

    public String getEvent_genre() {
        return event_genre;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public Integer getDday() {
        return dday;
    }

    public void setEvent_day(String event_day) {
        this.event_day = event_day;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setEvent_genre(String event_genre) {
        this.event_genre = event_genre;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setDday(Integer dday) {
        this.dday = dday;
    }

    @Override
    public int compareTo(@NonNull EventInformation o) {
        if(dday > o.getDday()){
            return 1;
        }
        else if(dday < o.getDday()){
            return -1;
        }
        return 0;
    }
}
