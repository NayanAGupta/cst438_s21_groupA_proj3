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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

public class ViewRecipe extends AppCompatActivity {
    Toolbar toolbar;
    Button favButton;
    TextView recipeInstructions;
    ImageView recipeImage;

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

        String name = "BLAT";

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // test

//        recipeName.setText(name);
        String ingredients = "Mayo, Bacon, Lettuce, Avocado, Tomato, Bread";
        ingredients = ingredients.replace(", ","\n");

        String directions = "Cook bacon. Slice tomato. Cut avocado. " +
                "Spread Mayo on bread. Layer the tomatoes, bacon, and lettuce on bottom slice of bread. " +
                "Spread avocado on top slice. Top with second slice of bread. Plate and enjoy.";
        directions  = "- " + directions;
        directions = directions.replace(". ", ".\n- ");
        directions = directions.replace("! ", "!\n- ");
        recipeInstructions.setText("INGREDIENTS\n" + ingredients + "\n\nINSTRUCTIONS\n" + directions);


        String url = "https://static01.nyt.com/images/2020/08/18/dining/27Diaryrex4/27Diaryrex4-articleLarge.jpg";
        Picasso.get().load(url).resize(300, 300).centerCrop().into(recipeImage);

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