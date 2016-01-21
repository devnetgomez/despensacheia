package com.brzapps.janynne.dispensacheia;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brzapps.janynne.dispensacheia.sqlite.helper.DatabaseHelper;
import com.brzapps.janynne.dispensacheia.sqlite.model.Item;

public class NewEditItemActivity extends AppCompatActivity {

    DatabaseHelper db;

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

            // Criando o objeto com informações
            Item newItem = new Item(edtNameItem.getText().toString(), 1);

            newItemId = db.createItem(newItem);

            db.closeDB();

            btnSaveItem.setText(String.valueOf(newItemId));


        }
        catch (Exception e)
        {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                    .show();

        }
        finally {

            db.closeDB();

            Toast.makeText(getApplicationContext(), String.valueOf( newItemId),  Toast.LENGTH_LONG)
            .show();

            this.finish();
        }

    }
}
