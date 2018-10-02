package com.example.kartikmishra.bakingapp.RecipeDetails;

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
import android.widget.ImageView;

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

public class RecipeDetailFragment extends Fragment implements FetchIngredientsAsyncTask.OnTaskCompleted,
FetchStepsAsyncTask.OnTaskCompleted,RecipeStepsAdapter.ListItemClickListener{
    private static final String TAG = "RecipeDetailFragment";

    public static Intent intent;
    View view;
    public static RecipesModel recipesModel;
    public RecipeDetailIngredientsAdapter detailIngredientsAdapter;
    public   RecipeStepsAdapter recipeStepsAdapter;
    public static Ingredients ingredientsModel;
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
    private ImageView fav_symbol_iv;
    private String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


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


        View view = inflater.inflate(R.layout.recipe_detail_fragment,container,false);




        Intent intent = Objects.requireNonNull(getActivity()).getIntent();

        recipesModel = new RecipesModel();

         recipe_number = intent.getIntExtra("recipe_position",0);

        recipesModel = RecipesFragment.recipesList.get(recipe_number);
        getActivity().setTitle(RecipesFragment.recipesList.get(recipe_number).getName());
        fav_symbol_iv = view.findViewById(R.id.fav_symbol);

        setUpFavBtn();
        RecyclerView recipesIngredientsRecyclerView = view.findViewById(R.id.recipe_detail_ingredients_recycler_view);
        RecyclerView recipesStepsRecyclerView = view.findViewById(R.id.recipe_detail_steps_recycler_view);


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
        FetchIngredientsAsyncTask ingredientsAsyncTask = new FetchIngredientsAsyncTask(RecipeDetailFragment.this);
        ingredientsAsyncTask.execute(String.valueOf(recipe_number));

        FetchStepsAsyncTask stepsAsyncTask = new FetchStepsAsyncTask( RecipeDetailFragment.this);
        stepsAsyncTask.execute(String.valueOf(recipe_number));


        DividerItemDecoration itemDecoration = new DividerItemDecoration(recipesStepsRecyclerView.getContext(),StepsLayoutManager.getOrientation());
        recipesStepsRecyclerView.addItemDecoration(itemDecoration);

        return view;
    }


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


    public void setUpFavBtn(){
        final boolean[] isFav = {false};


        fav_symbol_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFav[0]==true){
                    fav_symbol_iv.setImageResource(R.drawable.favsymboldark);
                    isFav[0] = false;
                }
                else {
                    fav_symbol_iv.setImageResource(R.drawable.favsymbolred);
                    isFav[0] = true;
                }
            }
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("steps_list", (ArrayList<? extends Parcelable>) stepsList);
        outState.putStringArrayList("measure_list", (ArrayList<String>) measure);
        outState.putStringArrayList("ingredient_list", (ArrayList<String>) ingredient);
        outState.putStringArrayList("quantity", (ArrayList<String>) quantity);
    }

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



    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d(TAG, "onListItemClick: "+clickedItemIndex);

        Intent intent = new Intent(getActivity(),StepsVideoActivity.class);
        intent.putExtra("steps_item_position",clickedItemIndex);
        startActivity(intent);
    }
}
