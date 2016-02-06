package com.brzapps.janynne.despensacheia.sqlite.model;

import com.brzapps.janynne.despensacheia.sqlite.helper.DatabaseHelper;
import com.brzapps.janynne.despensacheia.sqlite.helper.IModel;

/**
 * Created by janynne on 18/01/16.
 */
public class Item implements IModel {

    int id = 0;
    String name = "";
    int icon = 0;
    long idCategory = 0;

    // constructors
    public Item() {

    }

    public Item(String name, int idCategory) {
        this.name = name;
        this.idCategory = idCategory;
    }

    public Item(int id, String name, int idCategory) {
        this.id = id;
        this.name = name;
        this.idCategory = idCategory;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name  = name;
    }

    public void setIdCategory(long idCategory) { this.idCategory = idCategory;    }

    public void setIcon(int icon){
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

    public long getCategoryId() { return this.idCategory;    }



}
