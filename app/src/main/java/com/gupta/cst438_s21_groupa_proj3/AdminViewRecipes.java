package com.gupta.cst438_s21_groupa_proj3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

public class AdminViewRecipes extends AppCompatActivity {

    Toolbar toolbar;
    TextView adminList;
    TextView adminViewUserWelcome;
    Button deleteButton;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_recipes);

        adminViewUserWelcome = findViewById(R.id.adminViewUserWelcome);
        adminList = findViewById(R.id.adminList);
        deleteButton = findViewById(R.id.userDeleteButton);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String welcomeMessage = "Here are all recipes" + "\n";
        adminList.append(welcomeMessage);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("recipe");
        query.findInBackground((recipes, e) -> {
            if(e == null){
                for(ParseObject recipe1: recipes){
                    String list = "";
                    String recipeName = recipe1.getString("name");
                    JSONArray ingredients = recipe1.getJSONArray("ingredientIDList");
                    list += "\n" + "Recipe Name: " + recipeName + "\n" + "Ingredients: " + ingredients;
                    adminList.append(list);
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminDeleteRecipe.class);
                startActivity(intent);
            }
        });



    }

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
//                Intent submit = new Intent(getApplicationContext(), Submit.class);
//                startActivity(submit);
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
