package com.brzapps.janynne.despensacheia.sqlite.helper;

import java.util.ArrayList;

/**
 * Created by janynne on 23/01/16.
 */
public  interface IDataModel {

    static final String TABLE_NAME = "";

    public String getTableName();

    public String createTableScript();

    public IModel get(long id);

    public ArrayList get(int[] ids);

    public ArrayList getAll();

    public long insert(IModel model);

    public int update(IModel model);

    public void delete(int[] ids);
}
