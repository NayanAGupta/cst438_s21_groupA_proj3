package com.gupta.cst438_s21_groupa_proj3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

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
    }
}