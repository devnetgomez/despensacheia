
package com.brzapps.janynne.despensacheia.sqlite.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.brzapps.janynne.despensacheia.sqlite.model.Category;
import com.brzapps.janynne.despensacheia.sqlite.model.Item;

import java.util.ArrayList;

/**
 * Created by janynne on 23/01/16.
 */
public class Categories implements  IDataModel{

    SQLiteDatabase db;
    static  final String TABLE_NAME = "categories";
    static final String PRIMARY_KEY = "id";
    static final String KEY_NAME = "name";
    static final String KEY_ICON = "icon";


    public Categories(SQLiteDatabase db)
    {
        this.db = db;
    }


    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String createTableScript() {
        return "CREATE TABLE " + TABLE_NAME + "("
                + PRIMARY_KEY + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT )";
    }

    @Override
    public Category get(long id) {

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + PRIMARY_KEY + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Category model = new Category();

        model.setId(c.getInt(c.getColumnIndex(PRIMARY_KEY)));
        model.setName((c.getString(c.getColumnIndex(KEY_NAME))));
        model.setIcon(Integer.valueOf(c.getString(c.getColumnIndex(KEY_ICON))));

        return model;
    }

    @Override
    public ArrayList<Category> get(int[] ids) {
        return null;
    }

    @Override
    public ArrayList<Category> getAll() {

        ArrayList<Category> list = new ArrayList<Category>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ KEY_NAME;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {

                Category model = new Category();

                model.setId(c.getInt(c.getColumnIndex(PRIMARY_KEY)));
                model.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                model.setIcon(Integer.valueOf(c.getString(c.getColumnIndex(KEY_ICON))));

                list.add(model);
            } while (c.moveToNext());
        }

        return list;

    }


    @Override
    public long insert(IModel model) {

        Category _model = (Category)model;

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, _model.getName());
        values.put(KEY_ICON, _model.getIcon());

        long id = db.insert(TABLE_NAME, null, values);

        return id;
    }

    @Override
    public int update(IModel model) {

        Category _model = (Category)model;

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, _model.getName());

        // updating row
        return db.update(TABLE_NAME, values, PRIMARY_KEY + " = ?",
                new String[] { String.valueOf(_model.getId()) });
    }

    @Override
    public void delete(int[] ids) {

        for (long id: ids){

            db.delete(TABLE_NAME, PRIMARY_KEY + " = ?",
                    new String[] { String.valueOf(id) });
        }

    }

    public int count()
    {
        String selectQuery = "SELECT COUNT ("+PRIMARY_KEY+") AS \"count\" FROM " + TABLE_NAME ;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
            return c.getInt(c.getColumnIndex("count"));

    }

    public int getItemsCount(int idCategory)
    {
        if(idCategory > 0) {
            String selectQuery = "SELECT COUNT (id) AS \"count\" FROM items "
                    + " WHERE idcategory =" + idCategory;

            Cursor c = db.rawQuery(selectQuery, null);

            if (c != null)
                c.moveToFirst();
            return c.getInt(c.getColumnIndex("count"));
        }
        else
        {
            return 0;
        }
    }

    public ArrayList<Item> getAttachedItems(int idCategory)
    {
        ArrayList<Item> list = new ArrayList<Item>();

        if(idCategory > 0) {


            String selectQuery = "SELECT id, name, icon FROM items "
                    + " WHERE idcategory =" + idCategory;


            Cursor c = db.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {
                do {

                    Item model = new Item();

                    model.setId(c.getInt(c.getColumnIndex("id")));
                    model.setName((c.getString(c.getColumnIndex("name"))));
                    model.setIcon(Integer.valueOf(c.getString(c.getColumnIndex("icon"))));
                    model.setIdCategory((long) idCategory);

                    list.add(model);

                } while (c.moveToNext());
            }
        }

        return list;

    }
}







