package com.example.kartikmishra.bakingapp.DatabaseProvider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class RecipesContract {

    public static final String CONTENT_AUTHORITY = "com.example.kartikmishra.bakingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_RECIPES = "recipes";

    public static final class RecipesEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECIPES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECIPES;

        public static final String TABLE_NAME = "recipes";

        public static final String COLUMN_RECIPES_ID = "recipesID";
        public static final String COLUMN_RECIPE_NAME = "recipeName";
        public static final String COLUMN_RECIPE_SERVINGS = "servings";
        public static final String COLUMN_RECIPE_INGREDIENT = "ingredient";
        public static final String COLUMN_RECIPE_MEASURE = "measure";
        public static final String COLUMN_RECIPE_QUANTITY = "quantity";
        public static final String COLUMN_RECIPE_STEP_SHORT_DESCRIPTION= "shortDescription";
        public static final String COLUMN_RECIPE_STEP_DESCRIPTION = "description";
        public static final String COLUMN_RECIPE_STEP_VIDEO_URL = "videoURL";
        public static final String COLUMN_RECIPE_STEP_THUMBNAIL_URL = "thumbnailURL";
    }
}
