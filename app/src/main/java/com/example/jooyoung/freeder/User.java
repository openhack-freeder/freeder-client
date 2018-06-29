package com.example.jooyoung.freeder;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    ArrayList<EventInformation> Myevent = new ArrayList<>();

    public User(EventInformation myevent){
        Myevent.add(myevent);
    }
}
