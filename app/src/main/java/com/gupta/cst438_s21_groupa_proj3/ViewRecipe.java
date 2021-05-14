package com.gupta.cst438_s21_groupa_proj3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewRecipe extends AppCompatActivity {
    Toolbar toolbar;
    Button favButton;
    TextView recipeInstructions;
    ImageView recipeImage;
    String name;
    String ingredients;
    String directions;
    String url;
    List<String> ingredientList = new ArrayList<>();
    List<String> recipesList = new ArrayList<>();
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
        setContentView(R.layout.activity_view_recipe);

        recipeImage = findViewById(R.id.viewRecipeImage);
        recipeInstructions = findViewById(R.id.viewRecipeInstructions);
        toolbar = findViewById(R.id.toolbar);
        favButton = findViewById(R.id.addFav);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //String givenObjectId = "Z2KJhCxOvm"; //placeholder for now, should be passed through intent
        Bundle extras = getIntent().getExtras();
        String givenObjectId = extras.getString("givenObjectId");
        //query for recipe given recipe Id
        ParseQuery<ParseObject> query = ParseQuery.getQuery("recipe");
        query.getInBackground(givenObjectId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    name = object.getString("name"); //get recipe name
                    directions = object.getString("description"); //get recipe desc/instruction
                    url = object.getString("imageURL");  //get img url

                    ingredientList = object.getList("ingredientIDList"); //get list of ingredients
                    ingredients = "";
                    for(String singleIngredient :ingredientList){
                        //convert list to single string

                        ingredients += singleIngredient;

                        Log.d("Book", ingredients);
                    }
                } else {
                    name = "error";
                    directions = "error "+e.getMessage();
                }
                getSupportActionBar().setTitle(name);
                ingredients = ingredients.replace(", ","\n");
                directions  = "- " + directions;
                directions = directions.replace(". ", ".\n- ");
                directions = directions.replace("! ", "!\n- ");
                recipeInstructions.setText("INGREDIENTS\n" + ingredients + "\n\nINSTRUCTIONS\n" + directions);

                Picasso.get().load(url).resize(500, 500).centerCrop().into(recipeImage);
            }
        });

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser currUser = ParseUser.getCurrentUser();
                String currUserBookId = currUser.getString("recipeBookId");
                Log.d("Book", currUserBookId);
                ParseQuery<ParseObject> query = ParseQuery.getQuery("recipeBook");
                query.getInBackground(currUserBookId, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null){
                            //found book
                            //add recipe id to book and upload to database
                            // check if book already has id
                            //Log.d("book", "Recent recipe id: "+recentRecipeId);
                            recipesList = object.getList("recipeIDList");
                            if (recipesList == null || !recipesList.contains(givenObjectId)) {
                                object.add("recipeIDList", givenObjectId);
                                object.saveInBackground();
                                Toast.makeText(getApplicationContext(), "Added recipe to your book", Toast.LENGTH_LONG).show();
                            }
                            else{

                                Toast.makeText(getApplicationContext(),"You already have this recipe to your book",Toast.LENGTH_LONG).show();
                            }


                        } else {
                            Toast.makeText(getApplicationContext(),"Error querying for recipe book: "+e.getMessage(),Toast.LENGTH_LONG).show();
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