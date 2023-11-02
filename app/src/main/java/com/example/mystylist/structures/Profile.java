package com.example.mystylist.structures;

import java.io.Serializable;

public class Profile implements Serializable {
    private final String name;
    public String getName(){
        return this.name;
    }
    public Profile(String name){

        this.name = name;
    }

}
