package com.brzapps.janynne.despensacheia.activities;


import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.brzapps.janynne.despensacheia.DashboardButtons;
import com.brzapps.janynne.despensacheia.R;
import com.brzapps.janynne.despensacheia.adapters.DashboardButtonsAdapter;
import com.brzapps.janynne.despensacheia.sqlite.helper.Categories;
import com.brzapps.janynne.despensacheia.sqlite.helper.DatabaseHelper;
import com.brzapps.janynne.despensacheia.sqlite.helper.Items;
import com.brzapps.janynne.despensacheia.sqlite.helper.Lists;
import com.brzapps.janynne.despensacheia.sqlite.model.Category;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;


public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    DatabaseHelper db;
    Items items;
    Categories categories;
    Lists lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Context context = getApplicationContext();

        SharedPreferences NomesBotoes = context.getSharedPreferences(
                "Fundo", Context.MODE_PRIVATE);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        db = new DatabaseHelper(getApplicationContext());

        items = new Items(db.getReadableDatabase());

        categories = new Categories(db.getReadableDatabase());

        lists = new Lists(db.getReadableDatabase());

        // DashButtons configuration
        loadDashButtons();

        TextView txtFeaturedButton = (TextView)findViewById(R.id.txvDashButton);
        ImageView imgFeaturedButton = (ImageView)findViewById(R.id.imvDashButton);
        LinearLayout dashbuttonLinearLayout = (LinearLayout)findViewById(R.id.dashbuttonLinearLayout);

        txtFeaturedButton.setText("Lançar consumo");
        imgFeaturedButton.getLayoutParams().width = 100;
        //imgFeaturedButton.setImageResource(R.drawable.input1);
        dashbuttonLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        dashbuttonLinearLayout.requestLayout();
        dashbuttonLinearLayout.getLayoutParams().height = 50;


    }

    private SearchView.OnQueryTextListener onSearchHome()
    {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(Home.this, query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(Home.this, newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        };
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);

        MenuItem itemMenuSearch = menu.findItem(R.id.actionSearch);

        if(itemMenuSearch != null) {
            SearchView searchViewHome = (SearchView) itemMenuSearch.getActionView();

            if(searchViewHome != null) {
                searchViewHome.setOnQueryTextListener(onSearchHome());
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {

            /* Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);*/
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_newedititem_activity) {

            Intent intentSingleItemActivity= new Intent(getApplicationContext(), SingleItemActivity.class);
            startActivity(intentSingleItemActivity);

        } else if (id == R.id.nav_neweditcategory_activity) {

        Intent intentNewEditItemActivity = new Intent(getApplicationContext(), NewEditCategoryActivity.class);
        startActivity(intentNewEditItemActivity);

        } else if (id == R.id.nav_lists) {

            Intent intentNewEditItemActivity = new Intent(getApplicationContext(), ListingItemsActivity.class);
            startActivity(intentNewEditItemActivity);

        } else if (id == R.id.nav_statistics) {

        } else if (id == R.id.nav_recipes) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implemnent the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Home Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.brzapps.janynne.despensacheia/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Home Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.brzapps.janynne.despensacheia/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    protected void loadDashButtons(){

      runOnUiThread(new Runnable() {
          @Override
          public void run() {
              ArrayList<DashboardButtons> dashButtons = new ArrayList<DashboardButtons>();


              dashButtons.add(new DashboardButtons(1, R.drawable.ic_shopping_basket_black_48dp, String.valueOf(items.count())+ " Itens"));
              dashButtons.add(new DashboardButtons(2, R.drawable.ic_local_offer_black_48dp, String.valueOf(categories.count()) + " Categorias"));
              dashButtons.add(new DashboardButtons(3, R.drawable.ic_receipt_black_48dp, String.valueOf(lists.count()) + " Listas"));

              DashboardButtonsAdapter adapterDash = new DashboardButtonsAdapter(getApplicationContext(), dashButtons);

              GridView gdvDash = (GridView)findViewById(R.id.grdDashboardButtons);

              gdvDash.setAdapter(adapterDash);

              gdvDash.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                 @Override
                                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                                     if (id == 1) {

                                                         Intent intentListingItemsActivity = new Intent(getApplicationContext(), ListingItemsActivity.class);
                                                         startActivity(intentListingItemsActivity);

                                                     }

                                                     if (id == 2) {

                                                         Intent intentListingCategoryActivity = new Intent(getApplicationContext(), ListingCategoryActivity.class);
                                                         startActivity(intentListingCategoryActivity);

                                                     }
                                                     if (id == 3) {

                                                         Intent intentListactivity = new Intent(getApplicationContext(), Listactivity.class);
                                                         startActivity(intentListactivity);

                                                     }
                                                 }
                                             }
              );

              GridView grdDashCategories = (GridView)findViewById(R.id.grdDashCategories);

              ArrayList<DashboardButtons> catDashButtons = new ArrayList<DashboardButtons>();

              ArrayList<Category> cat = new ArrayList<Category>();

              cat = categories.getAll();

              for (int i =0; i < cat.size(); i++)
              {
                  Category category = cat.get(i);

                  catDashButtons.add( new DashboardButtons((int)category.getId(), R.drawable.ic_receipt_black_48dp,category.getName()));

              }

              DashboardButtonsAdapter adapterCatDash = new DashboardButtonsAdapter(getApplicationContext(), catDashButtons);


              grdDashCategories.setAdapter(adapterCatDash);




          }
      });

    }
}
