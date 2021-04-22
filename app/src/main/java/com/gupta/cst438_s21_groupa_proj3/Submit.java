package com.gupta.cst438_s21_groupa_proj3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;

import java.util.Arrays;

public class Submit extends AppCompatActivity {

    EditText recipeName;
    EditText recipeList;
    EditText recipeDescription;
    EditText recipeImage;
    Button recipeSubmit;

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
                    recipe.addAllUnique("ingredientIDList", Arrays.asList(recipeList.getText().toString()));
                    recipe.put("description",recipeDescription.getText().toString());
                    recipe.put("imageURL",recipeImage.getText().toString());
                    recipe.saveInBackground();
                    Toast.makeText(getApplicationContext(),"Submission Successful",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}