package com.example.kartikmishra.bakingapp;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
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

@RunWith(AndroidJUnit4.class)
public class RecipeActivityRecipeNameTest {


    @Rule public ActivityTestRule<RecipesActivity> mActivityTestRule =
            new  ActivityTestRule<>(RecipesActivity.class);


    @Before
    public void init(){
        mActivityTestRule.getActivity().getFragmentManager().beginTransaction();
    }

    @Test
    public void check_recipe_name_of_first_item_in_recyclerView(){

        Espresso.onView(ViewMatchers.withId(R.id.recipesActivity_recipeName_textView))
                .perform(RecyclerViewActions.<RecipesAdapter.RecipesAdapterViewHolder>scrollToPosition(0))
                .check(ViewAssertions.matches(ViewMatchers.withText("Nutella Pie")));

    }
}
