package com.gupta.cst438_s21_groupa_proj3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class AdminDeleteUser extends AppCompatActivity {
    Toolbar toolbar;
    Spinner spinner;
    ArrayList<String> username;
    Button deleteButton;
    List<ParseUser> users = new ArrayList<>();

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
        setContentView(R.layout.activity_admin_delete_user);

        username = new ArrayList<>();
        spinner = (Spinner)findViewById(R.id.spinner);
        deleteButton = findViewById(R.id.deleteButton);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground((users, e) -> {
            if(e == null){
                for(ParseUser user1: users){
                    if (user1.getBoolean("admin")) {
                        continue;
                    }
                    String user = user1.getString("username");
                    username.add(user);
                    this.users.addAll(users);
                }
            }
            spinner.setAdapter(new ArrayAdapter<String>(AdminDeleteUser.this, android.R.layout.simple_spinner_dropdown_item, username));


        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int selected, long l) {
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //DELETE USER and their recipebook
                        deleteUser(selected);

                        //not nessecary, do this feature later
//                        //query for user using username
//                        ParseQuery<ParseUser> query = ParseUser.getQuery();
//                        query.whereEqualTo("username",user);
//                        query.getFirstInBackground(new GetCallback<ParseUser>() {
//                            @Override
//                            public void done(ParseUser object, ParseException e) {
//                                if (e == null){
//                                    Log.d("book","Found user " +object.getUsername() + " userId: " +object.getObjectId());
//                                    params.put("userId", object.getObjectId()); //add selected user to map for removal
//                                    //cloud code call
//                                    ParseCloud.callFunctionInBackground("deleteUserWithId", params, new FunctionCallback<Object>() {
//                                        public void done(Object result, ParseException e) {
//                                            if (e == null) {
//                                                // successfully removed user
//                                                Log.d("book","Found user:" +user+" has been removed");
//                                            } else{
//                                                Log.d("book", "Error trying to remove user:" +e.getMessage());
//                                            }
//                                        }
//                                    });
//
//                                } else {
//                                    Log.d("book","Error querying for user: " + e.getMessage());
//                                }
//                            }
//                        });
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void deleteUser(int selected) {
        String selectedUsername = users.get(selected).getUsername();
        Map<String, String> params = new HashMap<>();
        params.put("id", users.get(selected).getObjectId());
        ParseCloud.callFunctionInBackground("adminDeleteUser", params, new FunctionCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if (e == null) {
                    users.remove(selected);
                    Toast.makeText(AdminDeleteUser.this, "Deleted user: "+ selectedUsername, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AdminDeleteUser.this, "Error deleting user: "+ selectedUsername, Toast.LENGTH_SHORT).show();
                    return;
                }
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
