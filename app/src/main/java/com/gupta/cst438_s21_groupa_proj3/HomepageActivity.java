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

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class HomepageActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        toolbar = findViewById(R.id.toolbar);
        welcomeTextView = findViewById(R.id.welcomeTextView);

        setSupportActionBar(toolbar);

        String welcomeMessage = "Welcome, "+ ParseUser.getCurrentUser().getUsername() + "!";
        welcomeTextView.append(welcomeMessage);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("recipe");
        query.whereEqualTo("objectId", "nAD0ebDIcU");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject recipe, ParseException e) {
                if(e == null){
                    String list = "";
                    String recipeName = recipe.getString("name");
                    list += "\n" + "Recipe Name: " + recipeName;
                    welcomeTextView.append(list);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent home = new Intent(getApplicationContext(), HomepageActivity.class);
                startActivity(home);
                return true;
            case R.id.favorites:
                Intent favorites = new Intent(getApplicationContext(), Favorites.class);
                startActivity(favorites);
                return true;
            case R.id.search:
                Intent search = new Intent(getApplicationContext(), Search.class);
                startActivity(search);
                return true;
            case R.id.submit:
                Intent submit = new Intent(getApplicationContext(), Submit.class);
                startActivity(submit);
                return true;
            case R.id.preferences:
                Intent preferences = new Intent(getApplicationContext(), Preferences.class);
                startActivity(preferences);
                return true;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }


}