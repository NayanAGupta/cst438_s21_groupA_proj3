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
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Arrays;

public class Submit extends AppCompatActivity {

    Toolbar toolbar;

    EditText recipeName;
    EditText recipeList;
    EditText recipeDescription;
    EditText recipeImage;
    Button recipeSubmit;

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
                    recipe.addAllUnique("ingredientIDList", Arrays.asList(recipeList.getText().toString()));
                    recipe.put("description",recipeDescription.getText().toString());
                    recipe.put("imageURL",recipeImage.getText().toString());
                    recipe.saveInBackground();
                    Toast.makeText(getApplicationContext(),"Submission Successful",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                    startActivity(intent);
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