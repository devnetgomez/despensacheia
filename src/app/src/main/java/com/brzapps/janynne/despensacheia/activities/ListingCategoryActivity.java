package com.brzapps.janynne.despensacheia.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.brzapps.janynne.despensacheia.R;
import com.brzapps.janynne.despensacheia.adapters.ListCategoriesAdapter;
import com.brzapps.janynne.despensacheia.adapters.ListItemsAdapter;
import com.brzapps.janynne.despensacheia.sqlite.helper.Categories;
import com.brzapps.janynne.despensacheia.sqlite.helper.DatabaseHelper;
import com.brzapps.janynne.despensacheia.sqlite.helper.Items;
import com.brzapps.janynne.despensacheia.sqlite.model.Category;
import com.brzapps.janynne.despensacheia.sqlite.model.Item;

import java.util.ArrayList;

public class ListingCategoryActivity extends AppCompatActivity {


    ListView lvDetail;

    Context context = ListingCategoryActivity.this;

    ArrayList<Category> myList = new ArrayList();

    DatabaseHelper db;

    Categories categories;


    // Progress panel views
    ProgressBar progressLoadingItems;
    RelativeLayout progressLayout    ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_category);



        db = new DatabaseHelper(getApplicationContext());

        lvDetail = (ListView) findViewById(R.id.listviewCategoriesListing);

        categories = new Categories(db.getReadableDatabase());

        new FillCategoriesListTask().execute(lvDetail);
    }

    public class FillCategoriesListTask extends AsyncTask<View, Integer, ArrayList<Category>> {


        protected void onPreExecute()
        {
            progressLoadingItems = new ProgressBar(getApplicationContext());

            progressLayout = new RelativeLayout(getApplicationContext());

            progressLayout.setGravity(Gravity.CENTER);

            progressLayout.addView(progressLoadingItems);

            lvDetail.addHeaderView(progressLayout);
        }

        @Override
        protected ArrayList<Category> doInBackground(View... params) {

            categories = new Categories(db.getReadableDatabase());

            return categories.getAll();
        }

        protected void onPostExecute (ArrayList<Category> result)
        {
            lvDetail.setAdapter(new ListCategoriesAdapter(context, result));

            lvDetail.removeHeaderView(progressLayout);
        }
    }
}
