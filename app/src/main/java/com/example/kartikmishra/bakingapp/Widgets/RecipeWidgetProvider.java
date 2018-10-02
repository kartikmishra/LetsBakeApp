package com.example.kartikmishra.bakingapp.Widgets;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


import com.example.kartikmishra.bakingapp.R;
import com.example.kartikmishra.bakingapp.RecipeDetails.RecipeDetailActivity;
import com.example.kartikmishra.bakingapp.Recipes.RecipesActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {


    public static final String EXTRA_LABEL = "extra_label";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {



        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        CharSequence sequence = "Recipes";
        views.setTextViewText(R.id.appwidget_text,sequence);
        Intent intent = new Intent(context,RecipesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        RemoteViews views = null;
        for (int appWidgetId : appWidgetIds) {
          views = new RemoteViews(
                    context.getPackageName(),
                    R.layout.recipe_widget_provider);
        }


        Intent recipeIntent = new Intent(context,RecipesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,recipeIntent,0);
        assert views != null;
        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
        Intent intent = new Intent(context,RecipeWidgetRemoteViewsService.class);
        views.setRemoteAdapter(R.id.widgetListView,intent);

        Intent clickIntentTemplate = new Intent(context, RecipeDetailActivity.class);
        PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(clickIntentTemplate)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widgetListView, clickPendingIntentTemplate);
        appWidgetManager.updateAppWidget(appWidgetIds,views);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

