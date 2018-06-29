package com.example.jooyoung.freeder;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    ArrayList<EventInformation> Myevent;

    public User(EventInformation myevent){
        Myevent = new ArrayList<>();
    }

    public void setMyevent(EventInformation myevent) {
        Myevent.add(myevent);
    }
}
