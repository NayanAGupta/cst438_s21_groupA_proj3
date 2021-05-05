package com.gupta.cst438_s21_groupa_proj3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Submit extends AppCompatActivity {

    Toolbar toolbar;

    EditText recipeName;
    EditText recipeList;
    EditText recipeDescription;
    EditText recipeImage;
    Button recipeSubmit;
    String currUserBookId;

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
        setContentView(R.layout.activity_submit);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Submit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recipeName = findViewById(R.id.editTextRecipeName);
        recipeList = findViewById(R.id.editTextRecipeList);
        recipeDescription = findViewById(R.id.editTextRecipeDescription);
        recipeImage = findViewById(R.id.editTextRecipeImage);
        recipeSubmit = findViewById(R.id.recipeSubmit);

        recipeSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recipeName.getText().toString().isEmpty() || recipeList.getText().toString().isEmpty() || recipeDescription.getText().toString().isEmpty() || recipeImage.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Fill in all fields",Toast.LENGTH_LONG).show();
                }
                else{
                    ParseObject recipe = new ParseObject("recipe");
                    recipe.put("name",recipeName.getText().toString());
                    recipe.addAllUnique("ingredientIDList", Arrays.asList(recipeList.getText().toString())); //comma seperate with no spaces ex: ingredient1,ingredient2,
                    recipe.put("description",recipeDescription.getText().toString());
                    recipe.put("imageURL",recipeImage.getText().toString());
                    recipe.saveInBackground();

                    //get current user's recipebook
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    currUserBookId = currentUser.getString("recipeBookId");
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("recipeBook");
                    query.getInBackground(currUserBookId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null){
                                //found book
                                //find recipe that was added
                                ParseQuery<ParseObject> query2 = ParseQuery.getQuery("recipe");
                                query2.whereEqualTo("name",recipeName.getText().toString());
                                query2.orderByDescending("createdAt");
                                query2.getFirstInBackground(new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject recentRecipe, ParseException e) {
                                        if (e == null){
                                            //found recent recipe
                                            //add recent recipe id to book and upload to database
                                            String recentRecipeId = recentRecipe.getObjectId();
                                            //Log.d("book", "Recent recipe id: "+recentRecipeId);
                                            object.add("recipeIDList", recentRecipeId);
                                            object.saveInBackground();
                                        }
                                    }
                                });

                                Toast.makeText(getApplicationContext(),"Submission Successful",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),"Error querying for recipe book: "+e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
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