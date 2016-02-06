package com.brzapps.janynne.despensacheia;

/**
 * Created by janynne on 05/02/16.
 */
public class DashboardButtons {

    private String text;
    private int resource;
    private int id;



    public DashboardButtons(int _id, int _resource, String _text)
    {
        this.text = _text;
        this.resource = _resource;
        this.id = _id;
    }

    public String getText()
    {
        return  this.text;
    }

    public int getResource()
    {
        return this.resource;
    }

    public int getId()
    {
        return this.id;
    }
}
