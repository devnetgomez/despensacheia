package com.brzapps.janynne.despensacheia.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

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

    Items items;

    LayoutInflater customInflater;

    // Progress panel views
    ProgressBar progressLoadingItems;
    RelativeLayout progressLayout    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_items);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                finish();

                Intent intentSingleItemActivity= new Intent(getApplicationContext(), SingleItemActivity.class);

                startActivity(intentSingleItemActivity);
            }
        });

       customInflater = LayoutInflater.from(context);

        db = new DatabaseHelper(getApplicationContext());

        lvDetail = (ListView) findViewById(R.id.listviewItemsListing);

        items = new Items(db.getReadableDatabase());

        new FillItemsListTask().execute(lvDetail);


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


    public class FillItemsListTask extends AsyncTask<View, Integer, ArrayList<Item>> {


        protected void onPreExecute()
        {
            progressLoadingItems = new ProgressBar(getApplicationContext());

            progressLayout = new RelativeLayout(getApplicationContext());

            progressLayout.setGravity(Gravity.CENTER);

            progressLayout.addView(progressLoadingItems);

            lvDetail.addHeaderView(progressLayout);
        }

        @Override
        protected ArrayList<Item> doInBackground(View... params) {

            items = new Items(db.getReadableDatabase());

            return items.getAll();
        }

        protected void onPostExecute (ArrayList<Item> result)
        {
            lvDetail.setAdapter(new ListItemsAdapter(context, result));

            lvDetail.removeHeaderView(progressLayout);

            if(result.size() < 1) {

                // Embed a layout
                ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
                View v = customInflater.inflate(R.layout.row_items,view,false);


                TextView txtMessage = (TextView)v.findViewById(R.id.tvTitle);
                txtMessage.setText("Nothing added yet");

                TextView txtDesc = (TextView)v.findViewById(R.id.tvDesc);
                txtDesc.setText("You can click here to add an item now");

                TextView txtLetter = (TextView)v.findViewById(R.id.tvLetter);
                txtDesc.setText("i");

                ImageView imgMoreOptions = (ImageView)v.findViewById(R.id.imgMoreOptions);

                lvDetail.addHeaderView(v);

                lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intentSingleItemActivity = new Intent(getApplicationContext(), SingleItemActivity.class);
                        startActivity(intentSingleItemActivity);
                    }
                });

            }else {


                lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        finish();

                        Intent intentSingleItemActivity = new Intent(getApplicationContext(), SingleItemActivity.class);

                        intentSingleItemActivity.putExtra("id", id);

                        startActivity(intentSingleItemActivity);
                    }
                });
            }
        }
    }
}