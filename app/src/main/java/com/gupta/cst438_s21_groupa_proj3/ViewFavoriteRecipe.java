package com.gupta.cst438_s21_groupa_proj3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ViewFavoriteRecipe extends AppCompatActivity {

    TextView ViewFavRecipeName;
    TextView ViewFavRecipeList;
    TextView ViewFavRecipeDesc;
    TextView ViewFavRecipeURL;
    Button delFav;
    Button backFav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_favorite_recipe);
    }

    //backFav
}