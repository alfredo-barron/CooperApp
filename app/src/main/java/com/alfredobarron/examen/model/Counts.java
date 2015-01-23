package com.alfredobarron.examen.model;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.Timer;

public class Counts extends SugarRecord<Counts> {

    String name;
    int lot;
    String date;
    String time;
    boolean available;

    public Counts(){}

    public Counts(String name, int lot, String date, String time, boolean available) {
        this.name = name;
        this.lot = lot;
        this.date = date;
        this.time = time;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLot() {
        return lot;
    }

    public void setLot(int lot) {
        this.lot = lot;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
