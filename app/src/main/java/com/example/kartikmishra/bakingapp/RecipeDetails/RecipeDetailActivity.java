package com.example.kartikmishra.bakingapp.RecipeDetails;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kartikmishra.bakingapp.R;
import com.example.kartikmishra.bakingapp.RecipeSteps.StepsVideoActivity;
import com.example.kartikmishra.bakingapp.RecipeSteps.StepsVideoFragment;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragmentMasterList.OnStepClickListener{

    private static final String TAG = "RecipeDetailActivity";
    FragmentManager manager;
    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);



        if((findViewById(R.id.step_detail_linear_layout)!=null)){
            mTwoPane = true;

            if(savedInstanceState==null){
                StepsVideoFragment fragment = new StepsVideoFragment();
                manager.beginTransaction().add(R.id.recipestepVideoDetailContainer,fragment)
                .commit();
            }



        }else{
            mTwoPane=false;
        }

        RecipeDetailFragmentMasterList recipeDetailFragmentMasterList = new RecipeDetailFragmentMasterList();

        manager = getSupportFragmentManager();

        manager.beginTransaction()
                .add(R.id.recipeDetailContainerMasterList, recipeDetailFragmentMasterList,"MyFragment")
                .commit();

    }


    @Override
    public void onStepSelected(int position) {

        if(mTwoPane){
            StepsVideoFragment fragment = new StepsVideoFragment();
            manager.beginTransaction()
                    .replace(R.id.recipestepVideoDetailContainer,fragment)
                    .commit();
        }
        Intent intent = new Intent(this,StepsVideoActivity.class);
        intent.putExtra("steps_item_position",position);
        startActivity(intent);
    }
}
