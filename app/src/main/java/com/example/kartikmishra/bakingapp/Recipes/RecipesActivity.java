package com.example.kartikmishra.bakingapp.Recipes;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kartikmishra.bakingapp.R;

public class RecipesActivity extends AppCompatActivity {

    private static final String TAG = "RecipesActivity";
    RecipesFragment mContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipes);

        RecipesFragment recipesFragment = new RecipesFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.recipes_container,recipesFragment)
                .commit();
    }

}
