package com.gturedi;

import java.io.Serializable;

public class Car
        implements Serializable {

    public int id;
    public String title;

    public Car(int id, String title) {
        this.id = id;
        this.title = title;
    }

}
