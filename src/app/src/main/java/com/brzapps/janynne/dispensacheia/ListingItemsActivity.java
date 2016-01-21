package com.brzapps.janynne.dispensacheia;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.brzapps.janynne.dispensacheia.adapters.ListItemsAdapter;
import com.brzapps.janynne.dispensacheia.sqlite.helper.DatabaseHelper;
import com.brzapps.janynne.dispensacheia.sqlite.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListingItemsActivity extends AppCompatActivity {

    ListView lvDetail;
    Context context = ListingItemsActivity.this;
    ArrayList<Item> myList = new ArrayList();
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_items);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        db = new DatabaseHelper(getApplicationContext());


        lvDetail = (ListView) findViewById(R.id.listviewItemsListing);
        // insert data into the list before setting the adapter
        // otherwise it will generate NullPointerException  - Obviously

        myList = db.getAllItems();

        lvDetail.setAdapter(new ListItemsAdapter(context, myList));




    }


}