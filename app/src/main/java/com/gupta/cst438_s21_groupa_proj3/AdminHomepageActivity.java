package com.gupta.cst438_s21_groupa_proj3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

public class AdminHomepageActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView welcomeText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);

        welcomeText = findViewById(R.id.adminViewUserWelcome);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String welcomeMessage = "Welcome, Admin" + "!\n";
        welcomeText.setText(welcomeMessage);
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