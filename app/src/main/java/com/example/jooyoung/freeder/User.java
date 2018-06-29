package com.example.jooyoung.freeder;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private ArrayList<EventInformation> Myevent = new ArrayList<>();

    public User(){

    }

    public User(EventInformation myevent){
        Myevent = new ArrayList<>();
    }

    public void setMyevent(EventInformation myevent) {
        Myevent.add(myevent);
    }

    public ArrayList<EventInformation> getMyevent() {
        return Myevent;
    }
}
