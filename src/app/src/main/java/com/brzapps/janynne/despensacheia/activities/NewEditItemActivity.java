package com.brzapps.janynne.despensacheia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brzapps.janynne.despensacheia.R;
import com.brzapps.janynne.despensacheia.sqlite.helper.DatabaseHelper;
import com.brzapps.janynne.despensacheia.sqlite.helper.Items;
import com.brzapps.janynne.despensacheia.sqlite.model.Item;

public class NewEditItemActivity extends AppCompatActivity {

    DatabaseHelper db;
    Items items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_item);
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
    }



    public void btnSaveItem_onClick(View v) {

        // Instanciando os componentes
        Button btnSaveItem = (Button) findViewById(R.id.btnSaveItem);
        EditText edtNameItem = (EditText) findViewById(R.id.edtItemNameField);
        EditText edtStatusItem = (EditText) findViewById(R.id.edtItemStatusField);

        long newItemId = 0;

        try {

            if(!edtNameItem.getText().toString().isEmpty()) {
                // Criando o objeto com informações
                Item newItem = new Item(edtNameItem.getText().toString(), 1);

                items = new Items(db.getWritableDatabase());

                newItemId = items.insert(newItem);

                Toast.makeText(getApplicationContext(), "Great! "+ edtNameItem.getText().toString()+" added to list! ",  Toast.LENGTH_LONG)
                        .show();

            }
        }
        catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                    .show();

        }
        finally {

            db.closeDB();

            Toast.makeText(getApplicationContext(), String.valueOf( newItemId),  Toast.LENGTH_LONG)
            .show();

            this.finish();

            Intent intentNewEditItemActivity = new Intent(getApplicationContext(), ListingItemsActivity.class);
            startActivity(intentNewEditItemActivity);
        }

    }
}
