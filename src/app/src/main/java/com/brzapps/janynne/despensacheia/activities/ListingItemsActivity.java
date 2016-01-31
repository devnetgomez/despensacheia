package com.brzapps.janynne.despensacheia.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.brzapps.janynne.despensacheia.R;
import com.brzapps.janynne.despensacheia.adapters.ListItemsAdapter;
import com.brzapps.janynne.despensacheia.sqlite.helper.DatabaseHelper;
import com.brzapps.janynne.despensacheia.sqlite.helper.Items;
import com.brzapps.janynne.despensacheia.sqlite.model.Item;

import java.util.ArrayList;

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

        Items items = new Items(db.getReadableDatabase());

        myList = items.getAll();

        lvDetail.setAdapter(new ListItemsAdapter(context, myList));




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_list_item, menu);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            MenuItem itemMenuSearch = menu.findItem(R.id.actionSearchItems);

            if (itemMenuSearch != null) {
                SearchView searchViewHome = (SearchView) itemMenuSearch.getActionView();

                if (searchViewHome != null) {
                    searchViewHome.setOnQueryTextListener(onSearchListItems());
                }
            }
        }

        return true;
    }

    private SearchView.OnQueryTextListener onSearchListItems()
    {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Items items = new Items(db.getReadableDatabase());

                myList = items.getAll(query);

                lvDetail.setAdapter(new ListItemsAdapter(context, myList));

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.isEmpty())
                {

                    Items items = new Items(db.getReadableDatabase());

                    myList = items.getAll();

                    lvDetail.setAdapter(new ListItemsAdapter(context, myList));
                }
                return false;
            }


        };
    }

}