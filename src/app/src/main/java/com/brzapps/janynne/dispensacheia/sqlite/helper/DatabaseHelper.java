package com.brzapps.janynne.dispensacheia.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.brzapps.janynne.dispensacheia.sqlite.model.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by janynne on 18/01/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dispensacheia";

    // Table Names
    private static final String TABLE_ITEM = "item";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // NOTES Table - column nmaes
    private static final String KEY_NAME = "name";
    private static final String KEY_STATUS = "status";

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_TODO = "CREATE TABLE "
            + TABLE_ITEM + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_STATUS + " INTEGER," + KEY_CREATED_AT
            + " DATETIME" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_TODO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);

        // create new tables
        onCreate(db);
    }


    // CRUD functions


    /*
 * Creating an Item
 */
    public long createItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_STATUS, item.getStatus());
        values.put(KEY_CREATED_AT, new Date().getTime());

        // insert row
        long item_id = db.insert(TABLE_ITEM, null, values);

        return item_id;
    }

    /*
 * get single Item
 */
    public Item getItem(long item_id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_ITEM + " WHERE "
                + KEY_ID + " = " + item_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Item item = new Item();
        item.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        item.setName((c.getString(c.getColumnIndex(KEY_NAME))));
        item.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

        return item;
    }


    /*
 * getting all Items
 * */
    public List<Item> getAllToDos() {

        List<Item> items = new ArrayList<Item>();
        String selectQuery = "SELECT  * FROM " + TABLE_ITEM;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                item.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                item.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to todo list
                items.add(item);
            } while (c.moveToNext());
        }

        return items;
    }

    /*
 * Updating a Item
 */
    public int updateItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_STATUS, item.getStatus());

        // updating row
        return db.update(TABLE_ITEM, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
    }


    /*
 * Deleting a Item
 */
    public void deleteItem(long item_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEM, KEY_ID + " = ?",
                new String[] { String.valueOf(item_id) });
    }


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
