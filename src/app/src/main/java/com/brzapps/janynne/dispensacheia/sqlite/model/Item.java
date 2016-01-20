package com.brzapps.janynne.dispensacheia.sqlite.model;

/**
 * Created by janynne on 18/01/16.
 */
public class Item {

    int id;
    String name;
    int status;
    String created_at;

    // constructors
    public Item() {

    }

    public Item(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public Item(int id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name  = name;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCreatedAt(String created_at){
        this.created_at = created_at;
    }

    // getters
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getStatus() {
        return this.status;
    }
}
