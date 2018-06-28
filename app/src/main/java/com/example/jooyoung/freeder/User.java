package com.example.jooyoung.freeder;

import java.io.Serializable;

public class User implements Serializable {
    private String event_name;
    private String event_day; // 이벤트에 신청한 날짜
    private String event_deadline; // 이벤트 기간
    private String event_success; //발표 날짜

    public User(String event_name,String event_day,String event_deadline, String event_success){
        this.event_name = event_name;
        this.event_day = event_day;
        this.event_deadline = event_deadline;
        this.event_success = event_success;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_day() {
        return event_day;
    }

    public String getEvent_deadline() {
        return event_deadline;
    }


    public String getEvent_success() {
        return event_success;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public void setEvent_day(String event_day) {
        this.event_day = event_day;
    }

    public void setEvent_deadline(String event_deadline) {
        this.event_deadline = event_deadline;
    }

    public void setEvent_success(String event_success) {
        this.event_success = event_success;
    }
}
