package com.example.kartikmishra.bakingapp.RecipeSteps;

import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kartikmishra.bakingapp.R;

public class StepsVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_video);

        if(savedInstanceState==null){
            StepsVideoFragment stepsVideoFragment = new StepsVideoFragment();

            FragmentManager manager = getSupportFragmentManager();

            manager.beginTransaction()
                    .add(R.id.recipestepVideoDetailContainer,stepsVideoFragment)
                    .commit();
        }



    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            getSupportActionBar().hide();
        }
        else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT) {
            getSupportActionBar().show();
        }
    }
}
