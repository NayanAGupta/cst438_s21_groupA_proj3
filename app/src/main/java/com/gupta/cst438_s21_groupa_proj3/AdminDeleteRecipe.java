package com.gupta.cst438_s21_groupa_proj3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.ArrayList;

public class AdminDeleteRecipe extends AppCompatActivity {

    Spinner spinner;
    ArrayList<String> recipeList;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_recipe);
        recipeList = new ArrayList<>();
        spinner = (Spinner)findViewById(R.id.spinner);
        deleteButton = findViewById(R.id.deleteButton);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("recipe");
        query.findInBackground((recipes, e) -> {
            if(e == null){
                for(ParseObject recipe1: recipes){
                    String recipeName = recipe1.getString("name");
                    recipeList.add(recipeName);

                }
            }
            spinner.setAdapter(new ArrayAdapter<String>(AdminDeleteRecipe.this, android.R.layout.simple_spinner_dropdown_item, recipeList));

        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String recipe = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
