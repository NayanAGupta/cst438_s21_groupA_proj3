package com.gupta.cst438_s21_groupa_proj3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class Search extends AppCompatActivity {


    EditText search;
    Button searchButton;
    ScrollView scrollResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = findViewById(R.id.searchField);
        searchButton = findViewById(R.id.searchButton);
        scrollResult = findViewById(R.id.scrollResults);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("recipe");
                query.whereFullText("name", search.getText().toString());
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            if(objects.size() == 0){
                                Log.d("book", "No matches found");
                            } else{
                                for (ParseObject recipe : objects){
                                    Log.d("book", "Found using fulltext2: " + recipe.getString("name") + " ID: " + recipe.getObjectId());
                                }
                            }
                        } else {
                            Log.d("book", "Error searching for recipes: " + e.getMessage());
                        }
                    }
                });
            }
        });
    }
}