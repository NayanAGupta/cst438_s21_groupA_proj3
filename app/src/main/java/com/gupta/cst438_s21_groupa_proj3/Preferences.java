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
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Preferences extends AppCompatActivity {

    ParseUser currentUser;

    Toolbar toolbar;

    EditText oUN;
    EditText nUN;
    EditText nPW;
    EditText cNPW;
    Button confirm;
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
        setContentView(R.layout.activity_preferences);

        currentUser = ParseUser.getCurrentUser();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Preferences");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        oUN = findViewById(R.id.oUN);
        nUN = findViewById(R.id.nUN);
        nPW = findViewById(R.id.nPW);
        cNPW = findViewById(R.id.cPW);
        confirm =findViewById(R.id.confirmButton);

        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                    // all fields empty
                if(oUN.getText().toString().isEmpty() && nUN.getText().toString().isEmpty()
                        && nPW.getText().toString().isEmpty() && cNPW.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"No fields changed",Toast.LENGTH_SHORT).show();
                }
                    // username change
                if (!oUN.getText().toString().isEmpty() || !nUN.getText().toString().isEmpty()) {
                    if (oUN.getText().toString().equals(currentUser.get("username").toString())) {
                            // no new username
                        if (oUN.getText().toString().equals(nUN.getText().toString())) {
                            Toast.makeText(getApplicationContext(),"New Username is the Same",Toast.LENGTH_SHORT).show();
                        }
                        else if (nUN.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(),"No New Username",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            currentUser.setUsername(nUN.getText().toString());
                            String userBookId = currentUser.getString("recipeBookId"); //get book id
                            ParseQuery<ParseObject> query = ParseQuery.getQuery("recipeBook");
                            query.getInBackground(userBookId, new GetCallback<ParseObject>() {
                                public void done(ParseObject object, ParseException e) {
                                    if (e == null) {
                                        // object will be the book
                                        object.put("recipeBookTitle",nUN.getText().toString()+"'s book");
                                        object.saveInBackground();
                                    } else {
                                        // something went wrong
                                        Toast.makeText(getApplicationContext(),"Error querying for book:" +e,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            Toast.makeText(getApplicationContext(),"Username Changed!",Toast.LENGTH_SHORT).show();
                            currentUser.saveInBackground();
                        }
                    }
                    else {
                            // Username does not match
                        Toast.makeText(getApplicationContext(),"Username Does Not Match",Toast.LENGTH_SHORT).show();
                    }
                }
                    // password change
                if (!cNPW.getText().toString().isEmpty() && !nPW.getText().toString().isEmpty()) {
                        // new password matches check
                    if (cNPW.getText().toString().equals(nPW.getText().toString())) {
                        currentUser.setPassword(nPW.getText().toString());
                        Toast.makeText(getApplicationContext(),"Password Changed!",Toast.LENGTH_SHORT).show();
                        currentUser.saveInBackground();
                    }
                        // Password does not match check
                    else {
                        Toast.makeText(getApplicationContext(),"Passwords Do Not Match",Toast.LENGTH_SHORT).show();
                    }
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