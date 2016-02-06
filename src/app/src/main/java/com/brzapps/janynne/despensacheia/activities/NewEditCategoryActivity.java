package com.brzapps.janynne.despensacheia.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brzapps.janynne.despensacheia.R;
import com.brzapps.janynne.despensacheia.sqlite.helper.Categories;
import com.brzapps.janynne.despensacheia.sqlite.helper.DatabaseHelper;
import com.brzapps.janynne.despensacheia.sqlite.model.Category;

public class NewEditCategoryActivity extends AppCompatActivity {

    DatabaseHelper db;
    Categories categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_category);

        db = new DatabaseHelper(getApplicationContext());
    }

    public void btnSaveItem_onClick(View v) {

        // Instanciando os componentes
        Button btnSaveCategory = (Button) findViewById(R.id.btnSaveItem);
        EditText edtNameCategory = (EditText) findViewById(R.id.edtCategoryNameField);

        long newCategoryId = 0;

        try {

            if(!edtNameCategory.getText().toString().isEmpty()) {


                // Criando o objeto com informações
                Category newCategory = new Category(edtNameCategory.getText().toString());
                newCategory.setIcon(0);

                categories = new Categories(db.getWritableDatabase());

                newCategoryId = categories.insert(newCategory);

                Toast.makeText(getApplicationContext(), "Great! "+ newCategoryId + " - " +edtNameCategory.getText().toString()+" added to list! ",  Toast.LENGTH_LONG)
                        .show();

            }

        }
        catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Sorry! We could not add "+edtNameCategory.getText().toString()+" category to your list! ",  Toast.LENGTH_LONG)
                    .show();

        }
        finally {

            db.closeDB();



            this.finish();

        }

    }
}
