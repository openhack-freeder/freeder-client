package com.example.jooyoung.freeder;

import java.io.Serializable;

public class EventInformation implements Serializable {
    private String event_name;
    private String event_time;
    private String event_day;
    private String event_location;
    private String URL;
    private String event_genre;

    public EventInformation(String event_name,String event_time,String event_day,String event_location,String URL){
        this.event_name = event_name;
        this.event_day = event_day;
        this.event_location = event_location;
        this.event_time = event_time;
        this.URL = URL;

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
}
