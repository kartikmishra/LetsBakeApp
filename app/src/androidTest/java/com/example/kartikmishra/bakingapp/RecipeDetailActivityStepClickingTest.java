package com.example.kartikmishra.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.kartikmishra.bakingapp.RecipeDetails.RecipeDetailActivity;
import com.example.kartikmishra.bakingapp.RecipeSteps.StepsVideoFragment;
import com.example.kartikmishra.bakingapp.Recipes.RecipesActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityStepClickingTest  {

    @Rule public ActivityTestRule<RecipeDetailActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipeDetailActivity.class);

    @Rule public ActivityTestRule<RecipesActivity> mActivityTestRule1 =
            new ActivityTestRule<>(RecipesActivity.class);

    @Before
    public void init(){

        Intents.init();
        onView(withId(R.id.recipes_recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasExtraWithKey("recipe_position"));
        Intents.release();

    }

    @Test
    public void clickEvent_OnStep_PlaysCorrect_video(){

        Intents.init();
        onView(withId(R.id.recipe_detail_steps_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

    }
}
