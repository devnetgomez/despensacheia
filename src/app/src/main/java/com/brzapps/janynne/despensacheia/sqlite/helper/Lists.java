package com.brzapps.janynne.despensacheia.sqlite.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brzapps.janynne.despensacheia.sqlite.model.Item;
import com.brzapps.janynne.despensacheia.sqlite.model.List;

import java.util.ArrayList;

/**
 * Created by janynne on 06/02/16.
 */
public class Lists implements IDataModel {

    SQLiteDatabase db;

    static  final String TABLE_NAME = "lists";
    static final String PRIMARY_KEY = "id";
    static final String KEY_NAME = "name";
    static final String KEY_MONTH = "month";
    static final String KEY_YEAR = "year";

    public Lists(SQLiteDatabase db)
    {
        this.db = db;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public final String createTableScript() {
        return "CREATE TABLE " + TABLE_NAME + " ("
                + PRIMARY_KEY + " INTEGER AUTOINCREMENT PRIMARY KEY, "
                + KEY_NAME + " TEXT NOT NULL, "
                + KEY_MONTH + " INTEGER NOT NULL, "
                + KEY_YEAR  + " INTEGER NOT NULL )";
    }

    @Override
    public List get(long id) {

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + PRIMARY_KEY + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        List model = new List(c.getString(c.getColumnIndex(KEY_NAME)));

        model.setId(c.getInt(c.getColumnIndex(PRIMARY_KEY)));
        model.setMonth(c.getInt(c.getColumnIndex(KEY_MONTH)));
        model.setYear(c.getInt(c.getColumnIndex(KEY_YEAR)));

        return model;

    }

    @Override
    public ArrayList get(int[] ids) {
        return null;
    }

    @Override
    public ArrayList<List> getAll() {

        ArrayList<List> list = new ArrayList<List>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + "   ORDER BY "+ KEY_NAME;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                List model = new List(c.getString(c.getColumnIndex(KEY_NAME)));

                model.setId(c.getInt(c.getColumnIndex(PRIMARY_KEY)));
                model.setMonth(c.getInt(c.getColumnIndex(KEY_MONTH)));
                model.setYear(c.getInt(c.getColumnIndex(KEY_YEAR)));

                list.add(model);

            } while (c.moveToNext());
        }

        return list;
    }


    public ArrayList<List> getAll(String name) {

        ArrayList<List> list = new ArrayList<List>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + "  WHERE "+ KEY_NAME+" LIKE '%"+name+"%'  ORDER BY "+ KEY_NAME;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {

                List model = new List(c.getString(c.getColumnIndex(KEY_NAME)));

                model.setId(c.getInt(c.getColumnIndex(PRIMARY_KEY)));
                model.setMonth(c.getInt(c.getColumnIndex(KEY_MONTH)));
                model.setYear(c.getInt(c.getColumnIndex(KEY_YEAR)));

                list.add(model);

            } while (c.moveToNext());
        }

        return list;
    }


    @Override
    public long insert(IModel model) {

        List _model = (List)model;

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, _model.getName());
        values.put(KEY_MONTH, _model.getMonth());
        values.put(KEY_YEAR, _model.getYear());

        long id = db.insert(TABLE_NAME, null, values);

        return id;
    }

    @Override
    public int update(IModel model) {

        List _model = (List)model;

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, _model.getName());
        values.put(KEY_MONTH, _model.getMonth());
        values.put(KEY_YEAR, _model.getYear());

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

}
