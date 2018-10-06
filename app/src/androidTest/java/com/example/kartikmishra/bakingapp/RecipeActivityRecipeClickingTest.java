package com.example.kartikmishra.bakingapp;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.example.kartikmishra.bakingapp.RecipeDetails.RecipeDetailActivity;
import com.example.kartikmishra.bakingapp.Recipes.RecipesActivity;
import com.example.kartikmishra.bakingapp.Recipes.RecipesAdapter;
import com.example.kartikmishra.bakingapp.Recipes.RecipesFragment;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityRecipeClickingTest {


    @Rule public ActivityTestRule<RecipesActivity> mActivityTestRule =
            new  ActivityTestRule<>(RecipesActivity.class);


//    @Before
//    public void init(){
//        mActivityTestRule.getActivity().getFragmentManager().beginTransaction();
//    }

    @Test
    public void check_clickingevent_on_rv_opens_correct_deatil_view (){

        Intents.init();
        onView(withId(R.id.recipes_recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasExtraWithKey("recipe_position"));
        Intents.release();
    }
}
