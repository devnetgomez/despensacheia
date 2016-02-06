package com.brzapps.janynne.despensacheia.sqlite.model;

import com.brzapps.janynne.despensacheia.sqlite.helper.IModel;

/**
 * Created by janynne on 18/01/16.
 */
public class List implements IModel {

    int id = 0;
    String name = "";
    int month = 0;
    int year = 0;

    // constructors

    public List(String _name) {
        this.name = _name;
    }

    public List(String _name, int _month, int _year) {
        this.name = name;
        this.month = _month;
        this.year = _year;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name  = name;
    }

    public void setMonth(int monthNum) { this.month = monthNum;    }

    public void setYear(int yearNum){
        this.year = yearNum;
    }

    // getters
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() { return this.month;    }



}
