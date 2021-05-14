package com.gupta.cst438_s21_groupa_proj3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Favorites extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Toolbar toolbar;

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
        setContentView(R.layout.activity_favorites);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Favorites");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayList<ExampleItem> exampleList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        exampleList.clear();
        mAdapter.notifyDataSetChanged();
        ParseUser user = ParseUser.getCurrentUser();
        String currBook = user.getString("recipeBookId");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("recipeBook");
        query.getInBackground(currBook,new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    List<String> temp = object.getList("recipeIDList");
                    if(temp != null) {
                        for (String singleId : temp) {
                            ParseQuery<ParseObject> query2 = ParseQuery.getQuery("recipe");
                            query2.getInBackground(singleId, new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject recipe, ParseException e) {
                                    if (e == null) {
                                        exampleList.add(new ExampleItem(recipe.getString("imageURL"), recipe.getString("name"), "ID: " + recipe.getObjectId()));
                                        mAdapter.notifyItemInserted(exampleList.size() - 1);
                                        Log.d("book", "Found: " + recipe.getString("name") + " ID: " + recipe.getObjectId());
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error querying for recipe: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No favorites", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("book", "Error searching for recipe book: " + e.getMessage());
                }
            }
        });

        mAdapter.setOnItemClickerListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //mExampleList.get(position);

                Intent preferences = new Intent(getApplicationContext(), ViewRecipe.class);
                startActivity(preferences);
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