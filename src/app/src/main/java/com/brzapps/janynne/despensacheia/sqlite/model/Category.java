package com.brzapps.janynne.despensacheia.sqlite.model;

import com.brzapps.janynne.despensacheia.sqlite.helper.IModel;

/**
 * Created by janynne on 18/01/16.
 */
public class Category implements IModel{
    int id;
    String name;
    int icon;


    // constructors
    public Category() {

    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public Category(int id, String name, int icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name  = name;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }


    // getters
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getIcon() {
        return this.icon;
    }


}
