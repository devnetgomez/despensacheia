package com.brzapps.janynne.despensacheia.sqlite.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by janynne on 18/01/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dispensacheianew";

    // Logics Access
    public Items Items;
    public Categories Categories;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        //Items = new Items(this.getWritableDatabase());
        //Categories = new Categories(this.getWritableDatabase());

    }



    @Override
    public void onCreate(SQLiteDatabase db) {



        String itemsTableScript = "CREATE TABLE  items ("
                +  "id  INTEGER PRIMARY KEY, "
                + " name TEXT NOT NULL, "
                + "idcategory INTEGER NOT NULL, "
                + "icon TEXT NOT NULL ) ";

        db.execSQL(itemsTableScript);

        String categoriesTableScript = "CREATE TABLE  categories ("
                +  " id  INTEGER PRIMARY KEY, "
                + " name TEXT NOT NULL,"
                + " icon TEXT NOT NULL ) ";


        db.execSQL(categoriesTableScript);

        String personalListTableScript = "CREATE TABLE  lists ("
                +  " id  INTEGER PRIMARY KEY, "
                + " name TEXT NOT NULL,"
                + " month INTEGER , "
                + " year INTEGER  ) ";

        db.execSQL(personalListTableScript);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + Items.getTableName());
        db.execSQL("DROP TABLE IF EXISTS " + Categories.getTableName()
        );

        // create new tables
        onCreate(db);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


}