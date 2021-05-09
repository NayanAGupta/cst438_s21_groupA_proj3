package com.gupta.cst438_s21_groupa_proj3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class Search extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Toolbar toolbar;
    SearchView search;
    Button searchButton;

    // Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayList<ExampleItem> exampleList = new ArrayList<>();
//        exampleList.add(new ExampleItem(R.drawable.ic_android, "Pasta El Burro", "Italian"));
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
                                    exampleList.add(new ExampleItem(recipe.getString("imageURL"), recipe.getString("name"), "ID: " + recipe.getObjectId()));
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
    // Back Button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //  Switch to control the routing when using the drop tab in the toolbar.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //  Takes user to Home page
            case R.id.home:
                Intent home = new Intent(getApplicationContext(), HomepageActivity.class);
                startActivity(home);
                return true;
            //  Takes user to Favorites
            case R.id.favorites:
                Intent favorites = new Intent(getApplicationContext(), Favorites.class);
                startActivity(favorites);
                return true;
            //  Takes user to Search Menu
            case R.id.search:
                Intent search = new Intent(getApplicationContext(), Search.class);
                startActivity(search);
                return true;
            //  Takes user to Submit Form
            case R.id.submit:
                Intent submit = new Intent(getApplicationContext(), Submit.class);
                startActivity(submit);
                return true;
            //  Takes user to Preferences page
            case R.id.preferences:
                Intent preferences = new Intent(getApplicationContext(), Preferences.class);
                startActivity(preferences);
                return true;
            //  Logs user out
            case R.id.logout:
                ParseUser.logOut();
                Toast.makeText(getApplicationContext(),"You have been logged out.",Toast.LENGTH_LONG).show();
                Intent logout = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(logout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}