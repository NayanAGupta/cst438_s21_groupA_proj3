package com.gupta.cst438_s21_groupa_proj3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminDeleteRecipe extends AppCompatActivity {

    Toolbar toolbar;
    Spinner spinner;
    ArrayList<String> recipeList;
    Button deleteButton;
    List<ParseObject> recipes = new ArrayList<>();

    // Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_recipe);

        recipeList = new ArrayList<>();
        spinner = (Spinner)findViewById(R.id.spinner);
        deleteButton = findViewById(R.id.deleteButton);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("recipe");
        query.findInBackground((recipes, e) -> {
            if(e == null){
                for(ParseObject recipe1: recipes){
                    String recipeName = recipe1.getString("name");
                    recipeList.add(recipeName);
                    this.recipes.addAll(recipes);

                }
            }
            spinner.setAdapter(new ArrayAdapter<String>(AdminDeleteRecipe.this, android.R.layout.simple_spinner_dropdown_item, recipeList));

        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int selected, long l) {
                //String selectedRecipe = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteRecipe(selected);

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void deleteRecipe(int selected){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("recipe");
        Object recipe = recipes.get(selected).get("name");
        String selectedRecipe = recipes.get(selected).getObjectId();
        query.getInBackground(selectedRecipe,(object, e) -> {
            if(e == null){
                object.deleteInBackground(e2 ->{
                   if(e2 == null){
                       Toast.makeText(AdminDeleteRecipe.this, "Deleted recipe: "+ recipe, Toast.LENGTH_SHORT).show();

                   }
                   else{
                       Toast.makeText(AdminDeleteRecipe.this, "Error deleting: "+ recipe, Toast.LENGTH_SHORT).show();

                   }
                });
            }
        });

    }
    //  Options menu control switch
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //  Takes user to Home page
            case R.id.home:
                Intent home = new Intent(getApplicationContext(), AdminHomepageActivity.class);
                startActivity(home);
                return true;
            //  Takes user to Favorites
            case R.id.users:
                Intent users = new Intent(getApplicationContext(), AdminViewUsers.class);
                startActivity(users);
                return true;
            //  Takes user to Search Menu
            case R.id.recipes:
                Intent intent = new Intent(getApplicationContext(), AdminViewRecipes.class);
                startActivity(intent);
                return true;
            //  Takes user to Submit Form
            case R.id.approve:
                Intent approve = new Intent(getApplicationContext(), AdminApprove.class);
                startActivity(approve);
                return true;
            //  Takes user to Preferences page
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
