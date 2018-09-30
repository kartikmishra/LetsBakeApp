package com.example.kartikmishra.bakingapp.RecipeSteps;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kartikmishra.bakingapp.R;

public class StepsVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_video);

        StepsVideoFragment stepsVideoFragment = new StepsVideoFragment();

        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction()
                .add(R.id.recipestepVideoDetailContainer,stepsVideoFragment)
                .commit();
    }
}
