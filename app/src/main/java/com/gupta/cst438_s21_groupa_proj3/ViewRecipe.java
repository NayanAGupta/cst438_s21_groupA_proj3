package com.gupta.cst438_s21_groupa_proj3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ViewRecipe extends AppCompatActivity {

    Button favButton;
    Button returnButton;
    TextView recipeName;
    TextView recipeList;
    TextView recipeDirections;
    TextView recipeURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        favButton = findViewById(R.id.VRFavorite);
        returnButton = findViewById(R.id.VRreturnButton);
        recipeName = findViewById(R.id.viewRecipeName);
        recipeList = findViewById(R.id.viewRecipeList);
        recipeDirections = findViewById(R.id.viewRecipeDescription);
        recipeURL = findViewById(R.id.viewRecipeImageURL);

        returnButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent myIntent = new Intent(ViewRecipe.this, Search.class);
                ViewRecipe.this.startActivity(myIntent);
            }
        });
    }


}