package com.example.kartikmishra.bakingapp;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.kartikmishra.bakingapp.RecipeDetails.RecipeDetailActivity;
import com.example.kartikmishra.bakingapp.RecipeSteps.StepsVideoFragment;
import com.example.kartikmishra.bakingapp.Recipes.RecipesActivity;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityStepClickingTest  {

    @Rule public ActivityTestRule<RecipeDetailActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipeDetailActivity.class);

    @Rule public ActivityTestRule<RecipesActivity> mActivityTestRule1 =
            new ActivityTestRule<>(RecipesActivity.class);

    @Test
    public void clickEvent_OnStep_PlaysCorrect_video_and_description_tv_display_correct_description(){

        Intents.init();
        onView(withId(R.id.recipe_detail_steps_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withId(R.id.steps_description_tv))
                .check(ViewAssertions.matches(ViewMatchers.withText("Recipe Introduction")));

        Intents.release();
    }
}
