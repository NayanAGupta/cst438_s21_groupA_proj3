package com.gupta.cst438_s21_groupa_proj3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

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
//        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));
//        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));
//        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));
//        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));
//        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));
//        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));
//        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));
//        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        search = findViewById(R.id.searchField);
        searchButton = findViewById(R.id.searchButton);

        //scrollResult = findViewById(R.id.scrollResults);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exampleList.clear();
                mAdapter.notifyDataSetChanged();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("recipe");
                query.whereFullText("name", search.getQuery().toString());
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            if(objects.size() == 0){
                                Log.d("book", "No matches found");
                            } else{
                                for (ParseObject recipe : objects){
                                    exampleList.add(new ExampleItem(R.drawable.ic_android, recipe.getString("name"), "ID: " + recipe.getObjectId()));
                                    mAdapter.notifyItemInserted(exampleList.size()-1);
                                    Log.d("book", "Found: " + recipe.getString("name") + " ID: " + recipe.getObjectId());
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