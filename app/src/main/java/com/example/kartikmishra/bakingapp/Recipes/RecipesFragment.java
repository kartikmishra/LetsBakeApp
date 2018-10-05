package com.example.kartikmishra.bakingapp.Recipes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kartikmishra.bakingapp.R;
import com.example.kartikmishra.bakingapp.RecipeDetails.RecipeDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesFragment extends Fragment implements FetchRecipesAsyncTask.OnTaskCompleted,RecipesAdapter.ListItemClickListener {

    private static final String TAG = "RecipesFragment";
    public  RecipesAdapter recipesAdapter;
    public static List<RecipesModel> recipesList = new ArrayList<>();
    public static ArrayList<String> recipesName = new ArrayList<>();
    private String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @BindView(R.id.recipes_recyclerView) RecyclerView recipesRecyclerView;

    public static final int COL__RECIPE_ID=1;


    public RecipesFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if(savedInstanceState!=null && savedInstanceState.containsKey("recipeList")){

            recipesList =  savedInstanceState.getParcelableArrayList("recipeList");

        }

        FetchRecipesAsyncTask asyncTask = new FetchRecipesAsyncTask(RecipesFragment.this);
        asyncTask.execute(url);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipes,container,false);

        ButterKnife.bind(this,rootView);

        updateRecipes();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("recipeList", (ArrayList<? extends Parcelable>) RecipesFragment.recipesList);
    }


    /**
     * Updating the recipes here
     */
    public void updateRecipes(){


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recipesRecyclerView.setLayoutManager(layoutManager);
        recipesRecyclerView.setHasFixedSize(true);
        recipesRecyclerView.stopScroll();
        if(recipesAdapter!=null){
            recipesAdapter.notifyDataSetChanged();
        }
        else {
            recipesAdapter = new RecipesAdapter(getActivity(),recipesList,this);
            recipesRecyclerView.setAdapter(recipesAdapter);
        }

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recipesRecyclerView.getContext(),layoutManager.getOrientation());
        recipesRecyclerView.addItemDecoration(dividerItemDecoration);

    }


    /**
     * Updating the recipe list here to inflate in the recycler view
     * @param recipes
     */
    @Override
    public void onRecipesTaskCompleted(List<RecipesModel> recipes) {

        if(recipes!=null){
            if(recipes.size()>0){

                //recipesName.add(recipes.get(0).getName());

                recipesList = new ArrayList<>();
                recipesList.addAll(recipes);
                recipesAdapter.notifyDataSetChanged();
            }

        }

    }

    /**
     * Going to recipe detail activity from here when a recipe is selected
     * @param clickedItemIndex
     */
    @Override
    public void onListItemClick(int clickedItemIndex) {

        Intent intent = new Intent(getActivity(),RecipeDetailActivity.class);
        intent.putExtra("recipe_position",clickedItemIndex);
        startActivity(intent);
    }

}
