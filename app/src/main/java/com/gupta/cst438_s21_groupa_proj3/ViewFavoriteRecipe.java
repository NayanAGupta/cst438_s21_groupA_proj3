package com.gupta.cst438_s21_groupa_proj3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Arrays;

public class ViewFavoriteRecipe extends AppCompatActivity {

    TextView ViewFavRecipeName;
    TextView ViewFavRecipeList;
    TextView ViewFavRecipeDesc;
    TextView ViewFavRecipeURL;
    Button delFav;
    Button backFav;
    String recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_favorite_recipe);

        recipeId = "iKY3GeGDhv"; //currently hardcoded to ham sandwich until we can get id

        delFav = findViewById(R.id.VFRDelete);
        delFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get current user
                ParseUser currUser = ParseUser.getCurrentUser();
                //get current user's book
                String currUserBookId = currUser.getString("recipeBookId");
                //query for recipe book
                ParseQuery<ParseObject> query = ParseQuery.getQuery("recipeBook");
                query.getInBackground(currUserBookId, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null){
                            //found book
                            //use recipe id to remove from book and upload to database
                            //Log.d("book", "Recent recipe id: "+recentRecipeId);
                            //object.add("recipeIDList", recipeId);

                            String temp[] = new String[]{recipeId};
                            object.removeAll("recipeIDList", Arrays.asList(temp)); //remove all only handles collections so needed to convert single string
                            object.saveInBackground();

                            Toast.makeText(getApplicationContext(),"Removed recipe to your book",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"Error querying for recipe book: "+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    //backFav
}