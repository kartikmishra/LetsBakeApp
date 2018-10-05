package com.example.kartikmishra.bakingapp.Widgets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.kartikmishra.bakingapp.DatabaseProvider.RecipesContract;
import com.example.kartikmishra.bakingapp.R;
import com.example.kartikmishra.bakingapp.RecipeDetails.RecipeDetailFragmentMasterList;
import com.example.kartikmishra.bakingapp.Recipes.RecipesFragment;
import com.example.kartikmishra.bakingapp.Widgets.RecipeWidgetProvider;

public class RecipeWidgetRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = "RecipeWidgetRemoteViewF";
    private Context context;
    private Cursor cursor;
    private String recipe;

    public RecipeWidgetRemoteViewFactory(Context context, Intent intent) {
        this.context = context;
    }

    @Override
    public void onCreate() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("my_pref",0);
        recipe = sharedPreferences.getString("recipe_nutella_pie","hh");
        Log.d(TAG, "onCreate: shared: "+recipe);

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {


    }

    @Override
    public int getCount() {
        return RecipeDetailFragmentMasterList.ingredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(position == AdapterView.INVALID_POSITION){
            return null;
        }


        RemoteViews rv = new RemoteViews(context.getPackageName(),R.layout.recipe_widget_provider);
        if(recipe.equals(RecipesFragment.recipesList.get(0).getName())){
                rv.setTextViewText(R.id.appwidget_text,RecipeDetailFragmentMasterList.ingredient.get(position));
            }

            else if(RecipesFragment.recipesList.get(1).getName().equals("Brownies")){
            rv.setTextViewText(R.id.appwidget_text,RecipeDetailFragmentMasterList.ingredient.get(position));
        }
        else if(RecipesFragment.recipesList.get(2).getName().equals("Yellow Cake")){
            rv.setTextViewText(R.id.appwidget_text,RecipeDetailFragmentMasterList.ingredient.get(position));
        }else  if (RecipesFragment.recipesList.get(3).getName().equals("Cheese Cake")){
            rv.setTextViewText(R.id.appwidget_text,RecipeDetailFragmentMasterList.ingredient.get(position));
        }


        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
