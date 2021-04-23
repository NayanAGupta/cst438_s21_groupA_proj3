package com.gupta.cst438_s21_groupa_proj3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Submit extends AppCompatActivity {

    EditText recipeName;
    EditText recipeList;
    EditText recipeDescription;
    EditText recipeImage;
    Button recipeSubmit;

    List<String> recipeBookList= new ArrayList<String>();
    String currUserBookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

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
                    recipe.addAllUnique("ingredientIDList", Arrays.asList(recipeList.getText().toString())); //comma seperate with no spaces ex: ingredient1,ingredient2,
                    recipe.put("description",recipeDescription.getText().toString());
                    recipe.put("imageURL",recipeImage.getText().toString());
                    recipe.saveInBackground();

                    //get current user's recipebook
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    currUserBookId = currentUser.getString("recipeBookId");
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("recipeBook");
                    query.getInBackground(currUserBookId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null){
                                //found book
                                //get list of current recipeID in book
                                recipeBookList.addAll(object.getList("recipeIDList"));

                                //find recipe that was added
                                ParseQuery<ParseObject> query2 = ParseQuery.getQuery("recipe");
                                query2.whereEqualTo("name",recipeName.getText().toString());
                                query2.orderByDescending("createdAt");
                                query2.getFirstInBackground(new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject recentRecipe, ParseException e) {
                                        if (e == null){
                                            //found recent recipe
                                            //add recent recipe to list of current recipeID in book and upload to database
                                            String recentRecipeId = recentRecipe.getObjectId();
                                            Log.d("book", "Recent recipe id: "+recentRecipeId);

                                            //currently adds onto the prev list instead of replacing it

                                            object.add("recipeIDList", recentRecipeId);
                                            object.saveInBackground();
                                        }
                                    }
                                });

                                Toast.makeText(getApplicationContext(),"Submission Successful",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),"Error querying for recipe book: "+e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}