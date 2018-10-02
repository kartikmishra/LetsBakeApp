package com.example.kartikmishra.bakingapp.Widgets;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.kartikmishra.bakingapp.R;
import com.example.kartikmishra.bakingapp.Recipes.RecipesFragment;
import com.example.kartikmishra.bakingapp.Widgets.RecipeWidgetProvider;

public class RecipeWidgetRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;

    public RecipeWidgetRemoteViewFactory(Context context, Intent intent) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return RecipesFragment.recipesList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(position == AdapterView.INVALID_POSITION){
            return null;
        }


        RemoteViews rv = new RemoteViews(context.getPackageName(),R.layout.recipe_widget_provider);
        rv.setTextViewText(R.id.appwidget_text,RecipesFragment.recipesList.get(position).getName());

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
