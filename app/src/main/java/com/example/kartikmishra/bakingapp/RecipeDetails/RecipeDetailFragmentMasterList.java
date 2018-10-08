package com.example.kartikmishra.bakingapp.RecipeDetails;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ImageView;

import com.example.kartikmishra.bakingapp.DatabaseProvider.RecipesContract;
import com.example.kartikmishra.bakingapp.R;
import com.example.kartikmishra.bakingapp.RecipeIngredients.FetchIngredientsAsyncTask;
import com.example.kartikmishra.bakingapp.RecipeIngredients.Ingredients;
import com.example.kartikmishra.bakingapp.RecipeIngredients.RecipeDetailIngredientsAdapter;
import com.example.kartikmishra.bakingapp.RecipeSteps.FetchStepsAsyncTask;
import com.example.kartikmishra.bakingapp.RecipeSteps.RecipeStepsAdapter;
import com.example.kartikmishra.bakingapp.RecipeSteps.Steps;
import com.example.kartikmishra.bakingapp.RecipeSteps.StepsVideoActivity;
import com.example.kartikmishra.bakingapp.Recipes.RecipesFragment;
import com.example.kartikmishra.bakingapp.Recipes.RecipesModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeDetailFragmentMasterList extends Fragment implements FetchIngredientsAsyncTask.OnTaskCompleted,
FetchStepsAsyncTask.OnTaskCompleted,RecipeStepsAdapter.ListItemClickListener{
    private static final String TAG = "RecipeDetailFragmentMas";

    public static RecipesModel recipesModel;
    public RecipeDetailIngredientsAdapter detailIngredientsAdapter;
    public   RecipeStepsAdapter recipeStepsAdapter;
     public static List<Ingredients> ingredientsList = new ArrayList<>();
     public static  List<Steps> stepsList = new ArrayList<>();
     public static  List<String> ingredient = new ArrayList<>();
     public  static List<String> measure = new ArrayList<>();
     public  static  List<String> quantity = new ArrayList<>();
     public static List<Integer> ids = new ArrayList<>();
     public static List<String> shortDescription = new ArrayList<>();
     public static  List<String> description = new ArrayList<>();
     public static  List<String> videoURLs = new ArrayList<>();
     public  static  List<String> thumbnailURLs = new ArrayList<>();
    public static int recipe_number;
    private String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    @BindView(R.id.fav_symbol) ImageView fav_symbol_iv;
    @BindView(R.id.recipe_detail_ingredients_recycler_view) RecyclerView recipesIngredientsRecyclerView;
    @BindView(R.id.recipe_detail_steps_recycler_view) RecyclerView recipesStepsRecyclerView;
    private Unbinder unbinder;
    OnStepClickListener mCallBack;

    public interface OnStepClickListener{
        void onStepSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallBack = (OnStepClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement step click listener.");
        }
    }

    public RecipeDetailFragmentMasterList() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if(savedInstanceState!=null &&
                savedInstanceState.containsKey("steps_list")&& savedInstanceState.containsKey("measure_list")
                && savedInstanceState.containsKey("ingredient_list")&& savedInstanceState.containsKey("quantity")){
            savedInstanceState.getParcelableArrayList("steps_list");
            savedInstanceState.getStringArrayList("ingredient_list");
            savedInstanceState.getStringArrayList("measure_list");
            savedInstanceState.getStringArrayList("quantity");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.recipe_detail_fragment_master_list,container,false);

       unbinder=ButterKnife.bind(this,view);

        Intent intent = Objects.requireNonNull(getActivity()).getIntent();

        recipesModel = new RecipesModel();

         recipe_number = intent.getIntExtra("recipe_position",0);

        recipesModel = RecipesFragment.recipesList.get(recipe_number);
        getActivity().setTitle(RecipesFragment.recipesList.get(recipe_number).getName());

        setUpFavBtn();


        LinearLayoutManager IngredientLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        recipesIngredientsRecyclerView.setHasFixedSize(true);
        recipesIngredientsRecyclerView.setLayoutManager(IngredientLayoutManager);

        LinearLayoutManager StepsLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recipesStepsRecyclerView.setHasFixedSize(true);
        recipesStepsRecyclerView.setLayoutManager(StepsLayoutManager);


        if(detailIngredientsAdapter!=null){
            detailIngredientsAdapter.notifyDataSetChanged();
        }
        else {
            detailIngredientsAdapter = new RecipeDetailIngredientsAdapter(getActivity(),ingredientsList);
            recipesIngredientsRecyclerView.setAdapter(detailIngredientsAdapter);
        }

        if(recipeStepsAdapter!=null){
            recipeStepsAdapter.notifyDataSetChanged();
        }
        else {
            recipeStepsAdapter = new RecipeStepsAdapter(getActivity(),stepsList,this);
            recipesStepsRecyclerView.setAdapter(recipeStepsAdapter);
        }
        FetchIngredientsAsyncTask ingredientsAsyncTask = new FetchIngredientsAsyncTask(RecipeDetailFragmentMasterList.this);
        ingredientsAsyncTask.execute(String.valueOf(recipe_number));

        FetchStepsAsyncTask stepsAsyncTask = new FetchStepsAsyncTask( RecipeDetailFragmentMasterList.this);
        stepsAsyncTask.execute(String.valueOf(recipe_number));


        DividerItemDecoration itemDecoration = new DividerItemDecoration(recipesStepsRecyclerView.getContext(),StepsLayoutManager.getOrientation());
        recipesStepsRecyclerView.addItemDecoration(itemDecoration);

        setUpFavBtnState();
        setUpFavBtn();


        Log.d(TAG, "onCreateView: id:"+RecipesFragment.COL__RECIPE_ID);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_pref",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("recipe_nutella_pie",RecipesFragment.recipesList.get(0).getName());
        editor.apply();


        return view;
    }


    /**
     * Upadating the ingredient list here to show the selected ingredients in the recyclerView
     * @param ingredients
     */
    @Override
    public void onIngredientsTaskCompleted(List<Ingredients> ingredients) {


        if(ingredients!=null){
            if(ingredients.size()>0){

                ingredient.get(0);
                measure.get(0);
                quantity.get(0);

                ingredientsList.get(0);
                detailIngredientsAdapter.notifyDataSetChanged();
            }
        }
    }


    /**
     * Setting up favourite button state here
     */
    public void setUpFavBtnState(){

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                Cursor cursor = getContext().getContentResolver().query(
                        RecipesContract.RecipesEntry.CONTENT_URI,
                        null,
                        RecipesContract.RecipesEntry.COLUMN_RECIPES_ID + " = ?",
                        new String[]{String.valueOf(RecipesFragment.recipesList.get(recipe_number).getId())},null
                );

                boolean isFav=false;
                if(cursor!=null && cursor.moveToNext()) {
                    do {
                        if (cursor.getLong(RecipesFragment.COL__RECIPE_ID) == RecipesFragment.recipesList.get(recipe_number).getId()) {
                            isFav = true;
                        } else if (cursor.isNull(RecipesFragment.COL__RECIPE_ID)) {
                            isFav = false;
                        }

                    } while (cursor.moveToNext());
                    cursor.close();
                }
                return isFav;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if(aBoolean==true){
                    fav_symbol_iv.setImageResource(R.drawable.favsymbolred);
                }
                else {
                    fav_symbol_iv.setImageResource(R.drawable.favsymboldark);
                }
            }
        }.execute();
    }


    /**
     * Setting up favorite button here
     */
    public void setUpFavBtn(){
        fav_symbol_iv.setImageResource(R.drawable.favsymboldark);


        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... strings) {
                Cursor cursor = getContext().getContentResolver().query(
                        RecipesContract.RecipesEntry.CONTENT_URI,
                        null,
                        RecipesContract.RecipesEntry.COLUMN_RECIPES_ID + "=?",
                        new String[]{String.valueOf(RecipesFragment.recipesList.get(recipe_number).getId())},null
                );

                boolean isFav=false;
                if(cursor!=null && cursor.moveToNext()) {
                    do {
                        if (cursor.getLong(RecipesFragment.COL__RECIPE_ID) == RecipesFragment.recipesList.get(recipe_number).getId()) {
                            isFav = true;
                        } else if (cursor.isNull(RecipesFragment.COL__RECIPE_ID)) {
                            isFav = false;
                        }

                    } while (cursor.moveToNext());
                    cursor.close();
                }
                return isFav;
            }

            @Override
            protected void onPostExecute(final Boolean aBoolean) {

                fav_symbol_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(aBoolean==true){
                            new AsyncTask<Void, Void, Integer>() {
                                @Override
                                protected Integer doInBackground(Void... voids) {
                                    return getActivity().getApplicationContext().getContentResolver().delete(
                                            RecipesContract.RecipesEntry.CONTENT_URI,
                                            RecipesContract.RecipesEntry.COLUMN_RECIPES_ID + " = ?",
                                            new String[]{String.valueOf(RecipesFragment.recipesList.get(recipe_number).getId())}
                                    );
                                }
                                @Override
                                protected void onPostExecute(Integer integer) {
                                    Log.d(TAG, "onPostExecute: int:"+integer);
                                    fav_symbol_iv.setImageResource(R.drawable.favsymboldark);
                                    startActivity(getActivity().getIntent());
                                    getActivity().finish();

                                }
                            }.execute();
                        }else {
                            if(RecipesFragment.recipesList!=null){
                                new AsyncTask<String, Void, Uri>() {
                                    @Override
                                    protected Uri doInBackground(String... strings) {
                                        ContentValues cv = new ContentValues();
                                        cv.put(RecipesContract.RecipesEntry.COLUMN_RECIPES_ID,RecipesFragment.recipesList.get(recipe_number).getId());
                                        cv.put(RecipesContract.RecipesEntry.COLUMN_RECIPE_NAME,RecipesFragment.recipesList.get(recipe_number).getName());
                                        cv.put(RecipesContract.RecipesEntry.COLUMN_RECIPE_SERVINGS,RecipesFragment.recipesList.get(recipe_number).getServings());
                                        cv.put(RecipesContract.RecipesEntry.COLUMN_RECIPE_INGREDIENT,ingredientsList.get(recipe_number).getIngredient());
                                        cv.put(RecipesContract.RecipesEntry.COLUMN_RECIPE_MEASURE,ingredientsList.get(recipe_number).getMeasure());
                                        cv.put(RecipesContract.RecipesEntry.COLUMN_RECIPE_QUANTITY,ingredientsList.get(recipe_number).getQuantity());
                                        cv.put(RecipesContract.RecipesEntry.COLUMN_RECIPE_STEP_SHORT_DESCRIPTION,stepsList.get(recipe_number).getShortDescription());
                                        cv.put(RecipesContract.RecipesEntry.COLUMN_RECIPE_STEP_DESCRIPTION,stepsList.get(recipe_number).getDescription());
                                        if(stepsList.get(recipe_number).getVideoUrl()!=null){
                                            cv.put(RecipesContract.RecipesEntry.COLUMN_RECIPE_STEP_VIDEO_URL,stepsList.get(recipe_number).getVideoUrl());
                                        }
                                        else {
                                            cv.put(RecipesContract.RecipesEntry.COLUMN_RECIPE_STEP_VIDEO_URL,"");
                                        }


                                        return getActivity().getBaseContext().getContentResolver().insert(
                                                RecipesContract.RecipesEntry.CONTENT_URI,cv);

                                    }

                                    @Override
                                    protected void onPostExecute(Uri uri) {
                                        Log.d(TAG, "doInBackground: cv:"+uri);
                                        fav_symbol_iv.setImageResource(R.drawable.favsymbolred);
                                        startActivity(getActivity().getIntent());
                                        getActivity().finish();
                                    }
                                }.execute();
                            }
                        }
                    }
                });
            }
        }.execute();
    }


    /**
     * Saving the state of app here
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("steps_list", (ArrayList<? extends Parcelable>) stepsList);
        outState.putStringArrayList("measure_list", (ArrayList<String>) measure);
        outState.putStringArrayList("ingredient_list", (ArrayList<String>) ingredient);
        outState.putStringArrayList("quantity", (ArrayList<String>) quantity);
    }

    /**
     * Updating the steps here to show them in the recycler view
     * @param steps
     */
    @Override
    public void onStepsTaskCompleted(List<Steps> steps) {

        if(steps!=null){
            if(steps.size()>0){
                ids.get(0);
                shortDescription.get(0);
                description.get(0);
                videoURLs.get(0);
                thumbnailURLs.get(0);
                stepsList.get(0);
                recipeStepsAdapter.notifyDataSetChanged();
            }

        }
    }


    /**
     * Implemented the on step selected callback here to communicate with fragments
     * @param clickedItemIndex
     */

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d(TAG, "onListItemClick: "+clickedItemIndex);
        mCallBack.onStepSelected(clickedItemIndex);
    }

    /**
     * Unbinding the binder here
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
