package com.brzapps.janynne.despensacheia.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.brzapps.janynne.despensacheia.R;
import com.brzapps.janynne.despensacheia.adapters.ListCategoriesAdapter;
import com.brzapps.janynne.despensacheia.adapters.ListItemsAdapter;
import com.brzapps.janynne.despensacheia.sqlite.helper.Categories;
import com.brzapps.janynne.despensacheia.sqlite.helper.DatabaseHelper;
import com.brzapps.janynne.despensacheia.sqlite.helper.Items;
import com.brzapps.janynne.despensacheia.sqlite.model.Category;
import com.brzapps.janynne.despensacheia.sqlite.model.Item;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SingleItemActivity extends AppCompatActivity {

    DatabaseHelper db;

    Context context = SingleItemActivity.this;

    Items items;

    Categories categories;

    long idCategorySelected = 0;

    ListView lvwCategories;

    ArrayList<Category> myList = new ArrayList();

    ListCategoriesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(getApplicationContext());

        if(getIntent().hasExtra("id"))
        {
            items = new Items(db.getReadableDatabase());

            loadCurrentItemsData((long) getIntent().getExtras().get("id"));
        }


        lvwCategories = (ListView) findViewById(R.id.lvwCategories);


        lvwCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position,
                                 long id) {

             idCategorySelected = id;

             LinearLayout layout = (LinearLayout)view;

             layout.setBackgroundColor(Color.GRAY);

             Toast.makeText(getBaseContext(), "Position: " + String.valueOf(position) + " - Id: " + String.valueOf(id), Toast.LENGTH_LONG).show();

         }
        });


        categories = new Categories(db.getReadableDatabase());

        new FillCategoriesListTask().execute(lvwCategories);
    }

    public void loadCurrentItemsData(long id){

      if(items != null) {

          Item current = items.get(id);

          EditText edtItemNameField = (EditText) findViewById(R.id.edtItemNameField);
          edtItemNameField.setText(current.getName());

          Toast.makeText(getBaseContext(), current.getName(), Toast.LENGTH_LONG).show();
      }
    }


    public class FillCategoriesListTask extends AsyncTask<View, Integer, ArrayList<Category>> {


        protected void onPreExecute()
        {
           /* progressLoadingItems = new ProgressBar(getApplicationContext());

            progressLayout = new RelativeLayout(getApplicationContext());

            progressLayout.setGravity(Gravity.CENTER);

            progressLayout.addView(progressLoadingItems);

            lvDetail.addHeaderView(progressLayout);*/
        }

        @Override
        protected ArrayList<Category> doInBackground(View... params) {

            return categories.getAll();
        }

        protected void onPostExecute (ArrayList<Category> result)
        {
            adapter = new ListCategoriesAdapter(context, result);

            lvwCategories.setAdapter(adapter);

            //vDetail.removeHeaderView(progressLayout);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_single_item, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       int id = item.getItemId();

       if (id == R.id.actionSaveItemChanges) {

           // Instanciando os componentes
           EditText edtNameItem = (EditText) findViewById(R.id.edtItemNameField);

           ListView lvwCategories = (ListView) findViewById(R.id.lvwCategories);

           long newItemId = 0;

           try {

               if (!edtNameItem.getText().toString().isEmpty()) {

                   items = new Items(db.getWritableDatabase());

                   if(getIntent().hasExtra("id"))
                   {
                       Item oldItem = items.get((long)getIntent().getExtras().get("id"));

                       oldItem.setName(edtNameItem.getText().toString());
                       oldItem.setIdCategory(idCategorySelected);

                       items.update(oldItem);
                   }
                   else {

                       Item newItem = new Item(edtNameItem.getText().toString(), 1);

                       newItem.setIdCategory(idCategorySelected);

                       newItemId = items.insert(newItem);

                       Log.v("DESPENSACHEIA", edtNameItem.getText().toString() + " added to list");
                   }

               }
           } catch (Exception e) {

               Log.v("DESPENSACHEIA", e.getMessage());

           } finally {


               db.closeDB();
               Log.v("DESPENSACHEIA", "Database closed");

               finish();

               Intent intentNewEditItemActivity = new Intent(getApplicationContext(), ListingItemsActivity.class);
               startActivity(intentNewEditItemActivity);
           }
        }

        return super.onOptionsItemSelected(item);
    }


}
