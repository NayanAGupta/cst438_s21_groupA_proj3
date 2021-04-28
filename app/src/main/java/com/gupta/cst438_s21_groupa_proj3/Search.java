package com.gupta.cst438_s21_groupa_proj3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    SearchView search;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        ArrayList<ExampleItem> exampleList = new ArrayList<>();
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        search = findViewById(R.id.searchField);
        searchButton = findViewById(R.id.searchButton);

    }
}