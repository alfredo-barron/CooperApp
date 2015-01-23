package com.alfredobarron.examen.model;

import com.orm.SugarRecord;

public class Users extends SugarRecord<Users> {

    String name;
    int lot;
    long count;

    public Users(){}

    public Users(String name, int lot, long count) {
        this.name = name;
        this.lot = lot;
        this.count = count;
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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

}
