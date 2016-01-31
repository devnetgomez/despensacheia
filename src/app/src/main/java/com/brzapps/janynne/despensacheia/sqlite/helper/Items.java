package com.brzapps.janynne.despensacheia.sqlite.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brzapps.janynne.despensacheia.sqlite.model.Item;

import java.util.ArrayList;

/**
 * Created by janynne on 23/01/16.
 */
public class Items implements IDataModel {

    SQLiteDatabase db;

    static  final String TABLE_NAME = "items";
    static final String PRIMARY_KEY = "id";
    static final String KEY_NAME = "name";
    static final String KEY_ICON = "icon";
    static final String KEY_ID_CATEGORY = "idcategory";

    public Items(SQLiteDatabase db)
    {
        this.db = db;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String createTableScript() {
        return "CREATE TABLE " + TABLE_NAME + " ("
                + PRIMARY_KEY + " INTEGER AUTOINCREMENT PRIMARY KEY, "
                + KEY_NAME + " TEXT NOT NULL, "
                + KEY_ID_CATEGORY + " INTEGER NOT NULL, "
                + KEY_ICON   + " TEXT NOT NULL )";
    }

    @Override
    public Item get(long id) {

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + PRIMARY_KEY + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Item model = new Item();

        model.setId(c.getInt(c.getColumnIndex(PRIMARY_KEY)));
        model.setName((c.getString(c.getColumnIndex(KEY_NAME))));
        model.setIcon((c.getString(c.getColumnIndex(KEY_ICON))));
        model.setIdCategory(c.getInt(c.getColumnIndex(KEY_ID_CATEGORY)));

        return model;

    }

    @Override
    public ArrayList get(int[] ids) {
        return null;
    }

    @Override
    public ArrayList<Item> getAll() {

        ArrayList<Item> list = new ArrayList<Item>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + "   ORDER BY "+ KEY_NAME;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {

                Item model = new Item();

                model.setId(c.getInt(c.getColumnIndex(PRIMARY_KEY)));
                model.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                model.setIcon((c.getString(c.getColumnIndex(KEY_ICON))));
                model.setIdCategory(Integer.valueOf(c.getString(c.getColumnIndex(KEY_ID_CATEGORY))));

                list.add(model);
            } while (c.moveToNext());
        }

        return list;
    }

    @Override
    public long insert(IModel model) {

        Item _model = (Item)model;

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, _model.getName());
        values.put(KEY_ICON, _model.getIcon());
        values.put(KEY_ID_CATEGORY, _model.getCategoryId());

        long id = db.insert( TABLE_NAME, null, values);

        return id;
    }

    @Override
    public int update(IModel model) {

        Item _model = (Item)model;

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, _model.getName());
        values.put(KEY_ICON, _model.getIcon());
        values.put(KEY_ID_CATEGORY, _model.getCategoryId());

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
}
