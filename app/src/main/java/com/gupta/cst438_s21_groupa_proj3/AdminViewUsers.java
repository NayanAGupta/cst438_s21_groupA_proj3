package com.gupta.cst438_s21_groupa_proj3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

public class AdminViewUsers extends AppCompatActivity {

    Toolbar toolbar;
    TextView adminList;
    TextView adminViewUserWelcome;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_user);

        adminViewUserWelcome = findViewById(R.id.adminViewUserWelcome);
        adminList = findViewById(R.id.adminList);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String welcomeMessage = "Here are all users" + "\n";
        adminList.append(welcomeMessage);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground((users, e) -> {
            if(e == null){
                for(ParseUser user1: users){
                    String list = "";
                    String username = user1.getString("username");
                    String password = user1.getString("password");
                    list += "Username: " + username + "\n" + "Password: " + password + "\n";
                    adminList.append(list);
                }
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
//                Intent search = new Intent(getApplicationContext(), Search.class);
//                startActivity(search);
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
