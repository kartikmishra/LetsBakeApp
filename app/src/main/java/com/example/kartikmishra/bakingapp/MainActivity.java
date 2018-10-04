package com.example.kartikmishra.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.kartikmishra.bakingapp.RecipeDetails.RecipeDetailFragment;
import com.example.kartikmishra.bakingapp.RecipeSteps.StepsVideoFragment;
import com.example.kartikmishra.bakingapp.Recipes.RecipesFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private boolean twoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.mLinearLayout)!=null){
            twoPane=true;

            FragmentManager manager = getSupportFragmentManager();
            RecipesFragment recipeDetailFragment = new RecipesFragment();
            manager.beginTransaction().add(R.id.recipeDetailContainers,recipeDetailFragment)
                    .commit();

            StepsVideoFragment stepsVideoFragment = new StepsVideoFragment();
            manager.beginTransaction().add(R.id.recipestepVideoDetailContainers,stepsVideoFragment)
            .commit();
        }else {
            twoPane=false;
        }
    }
}
