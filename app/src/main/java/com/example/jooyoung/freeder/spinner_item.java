package com.example.jooyoung.freeder;

public class spinner_item {

    private String name;
    private int _flag_id;
    public spinner_item(String _name,int _flag_id){
        name = _name;
        this._flag_id = _flag_id;
    }

    public int get_flag_id() {
        return _flag_id;
    }

    public String getName() {
        return name;
    }

    public void set_flag_id(int _flag_id) {
        this._flag_id = _flag_id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
