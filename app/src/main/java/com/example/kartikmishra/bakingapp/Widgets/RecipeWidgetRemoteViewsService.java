package com.example.kartikmishra.bakingapp.Widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.kartikmishra.bakingapp.Widgets.RecipeWidgetRemoteViewFactory;

public class RecipeWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetRemoteViewFactory(this.getApplicationContext(),intent);
    }
}
