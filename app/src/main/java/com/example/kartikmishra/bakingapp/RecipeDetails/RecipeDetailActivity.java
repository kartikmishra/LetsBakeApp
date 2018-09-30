package com.example.kartikmishra.bakingapp.RecipeDetails;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kartikmishra.bakingapp.R;
import com.example.kartikmishra.bakingapp.RecipeSteps.StepsVideoFragment;

public class RecipeDetailActivity extends AppCompatActivity{

    private static final String TAG = "RecipeDetailActivity";
    FragmentManager manager;
    StepsVideoFragment stepsVideoFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);



        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        stepsVideoFragment= new StepsVideoFragment();


        manager = getSupportFragmentManager();

        manager.beginTransaction()
                .replace(R.id.recipeDetailContainer,recipeDetailFragment,"MyFragment")
                .commit();

    }


}
