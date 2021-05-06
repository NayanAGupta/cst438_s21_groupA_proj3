package com.gupta.cst438_s21_groupa_proj3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

public class ViewRecipe extends AppCompatActivity {
    Toolbar toolbar;
    Button favButton;
    Button returnButton;
    TextView recipeName;
    TextView recipeIngredients;
    TextView recipeDirections;
    TextView recipeURL;

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

        favButton = findViewById(R.id.VRFavorite);
        returnButton = findViewById(R.id.VRreturnButton);
        recipeName = findViewById(R.id.viewRecipeName);
        recipeIngredients = findViewById(R.id.viewRecipeIngredients);
        recipeDirections = findViewById(R.id.viewRecipeDescription);
        recipeURL = findViewById(R.id.viewRecipeImageURL);

        String name = "BLT";

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // test

        recipeName.setText(name);
        String ingredients = "Mayo, Bacon, Lettuce, Tomato, Bread";
        recipeIngredients.setText(ingredients);
        String directions = "Spread Mayo on bread. Layer the tomatoes, bacon, and lettuce between two slices of bread.";
        recipeDirections.setText(directions);
        String url = "https://static01.nyt.com/images/2020/08/18/dining/27Diaryrex4/27Diaryrex4-articleLarge.jpg";
        recipeURL.setText(url);

        returnButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent myIntent = new Intent(ViewRecipe.this, Search.class);
                ViewRecipe.this.startActivity(myIntent);
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