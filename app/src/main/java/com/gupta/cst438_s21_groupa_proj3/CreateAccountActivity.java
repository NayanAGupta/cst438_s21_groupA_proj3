package com.gupta.cst438_s21_groupa_proj3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;
import java.util.Arrays;

public class CreateAccountActivity extends AppCompatActivity {

    EditText usernameText;
    EditText passwordText;
    Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        usernameText = findViewById(R.id.createUsernameEditText);
        passwordText = findViewById(R.id.createPasswordEditText);
        createAccountButton = findViewById(R.id.createAccountButton);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!usernameText.getText().toString().isEmpty() && !passwordText.getText().toString().isEmpty()){
                    ParseUser user = new ParseUser();
                    user.setUsername(usernameText.getText().toString());
                    user.setPassword((passwordText.getText().toString()));
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                //valid account creation; make the recipe book for this user
                                ParseObject newBook = new ParseObject("recipeBook");
                                newBook.put("recipeBookTitle",usernameText.getText().toString()+"'s book");
                                String[] recipeIds = {"testRecipeId1","testRecipeId2","testRecipeId3"};
                                newBook.put("recipeIDList",Arrays.asList(recipeIds));
                                newBook.saveInBackground();

                                //update the user to be associated with this book
                                //query for the new user
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
                                query.whereEqualTo("username","jtest6");
                                query.getFirstInBackground(new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject foundUser, ParseException e) {
                                        if (foundUser != null) {
                                            Toast.makeText(getApplicationContext(),"found user:",Toast.LENGTH_LONG).show();
//                                            foundUser.put("recipeBookId","newBook.getObjectId()");
//                                            foundUser.saveInBackground();
                                        } else {
                                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                                Toast.makeText(getApplicationContext(),"Sign up successful",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                                startActivity(intent);
                            }
                            else{
                                //invalid account creation
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}