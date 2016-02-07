package com.brzapps.janynne.despensacheia.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.brzapps.janynne.despensacheia.R;
import com.brzapps.janynne.despensacheia.sqlite.helper.Items;
import com.brzapps.janynne.despensacheia.sqlite.model.Item;
import com.brzapps.janynne.despensacheia.sqlite.model.List;
import com.brzapps.janynne.despensacheia.sqlite.helper.DatabaseHelper;
import com.brzapps.janynne.despensacheia.sqlite.helper.Lists;

import java.util.ArrayList;

public class SingleListActivity extends AppCompatActivity {

    DatabaseHelper db;

    String[] months = new String[]{"JAN", "FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};

    String[] years = new String[]{"2016","2015"};

    Context context = SingleListActivity.this;

    Lists lists;

    String idMonthSelected ;
    String idYearSelected ;

    ListView lvwLists;
    EditText edtListName;
    Spinner spnMonth, spnYear;

    ArrayList<com.brzapps.janynne.despensacheia.sqlite.model.List> myList = new ArrayList();

    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_list);


        db = new DatabaseHelper(getApplicationContext());

        edtListName = (EditText) findViewById(R.id.edtListName);
        spnMonth = (Spinner) findViewById(R.id.spnMonth);
        spnYear = (Spinner) findViewById(R.id.spnYear);

        if (getIntent().hasExtra("id")) {

            lists = new Lists(db.getReadableDatabase());

            loadCurrentItemsData((long) getIntent().getExtras().get("id"));
        }
        else
        {
            loadMonthData(spnMonth);
            loadYearData(spnYear);
        }
    }

    public void loadCurrentItemsData(long id){

        if(lists != null) {

            List current = lists.get(id);

            edtListName.setText(current.getName());

            loadMonthData(spnMonth);
            loadYearData(spnYear);

            // Select the value came from database
            //spnMonth.setSelection(current.getMonth());
            //spnYear.setSelection(current.getYear());

        }
    }


    public void loadMonthData(Spinner spninner){

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, months);

        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spninner.setAdapter(monthAdapter);

        spninner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idMonthSelected = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void loadYearData(Spinner spinner){

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, years);

        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(yearAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idYearSelected = years[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_single_actions, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.actionSaveChanges) {

            // Instanciando os componentes
            EditText edtListName = (EditText) findViewById(R.id.edtListName);

            long newItemId = 0;

            try {

                if (!edtListName.getText().toString().isEmpty()) {

                    lists = new Lists(db.getWritableDatabase());

                    if(getIntent().hasExtra("id"))
                    {
                        List oldItem = lists.get((long)getIntent().getExtras().get("id"));

                        oldItem.setName(edtListName.getText().toString());
                        oldItem.setMonth(Integer.valueOf(idMonthSelected));
                        oldItem.setYear(Integer.valueOf(idYearSelected));



                        lists.update(oldItem);
                    }
                    else {

                        List newItem = new List(edtListName.getText().toString());

                        newItem.setMonth(Integer.valueOf(idMonthSelected));
                        newItem.setYear(Integer.valueOf(idYearSelected));

                        newItemId = lists.insert(newItem);
                    }

                }
            } catch (Exception e) {

                Log.v("DESPENSACHEIA", e.getMessage());

            } finally {


                db.closeDB();
                Log.v("DESPENSACHEIA", "Database closed");

                finish();

                Intent intentListactivity = new Intent(getApplicationContext(), Listactivity.class);
                startActivity(intentListactivity);
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
