package com.brzapps.janynne.despensacheia.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.brzapps.janynne.despensacheia.R;
import com.brzapps.janynne.despensacheia.adapters.ListItemsAdapter;
import com.brzapps.janynne.despensacheia.adapters.ListsAdapter;
import com.brzapps.janynne.despensacheia.sqlite.helper.DatabaseHelper;
import com.brzapps.janynne.despensacheia.sqlite.helper.Items;
import com.brzapps.janynne.despensacheia.sqlite.helper.Lists;
import com.brzapps.janynne.despensacheia.sqlite.model.Item;
import com.brzapps.janynne.despensacheia.sqlite.model.List;

import java.util.ArrayList;

public class Listactivity extends AppCompatActivity {

    ListView lvDetail;

    Context context = Listactivity.this;

    ArrayList<List> myList = new ArrayList();

    DatabaseHelper db;

    Lists lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

                Intent intentSingleListActivity= new Intent(getApplicationContext(), SingleListActivity.class);

                startActivity(intentSingleListActivity);
            }
        });

        db = new DatabaseHelper(getApplicationContext());

        lvDetail = (ListView) findViewById(R.id.listviewLists);

        lists = new Lists(db.getReadableDatabase());

        new FillListsTask().execute(lvDetail);
    }

    public class FillListsTask extends AsyncTask<View, Integer, ArrayList<List>> {


        protected void onPreExecute()
        {

        }

        @Override
        protected ArrayList<List> doInBackground(View... params) {

            lists = new Lists(db.getReadableDatabase());

            return lists.getAll();
        }

        protected void onPostExecute (ArrayList<List> result)
        {
            lvDetail.setAdapter(new ListsAdapter(context, result));

            lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    finish();

                    Intent intentSingleListActivity= new Intent(getApplicationContext(), SingleListActivity.class);

                    intentSingleListActivity.putExtra("id",id);

                    startActivity(intentSingleListActivity);
                }
            });
        }
    }
}
