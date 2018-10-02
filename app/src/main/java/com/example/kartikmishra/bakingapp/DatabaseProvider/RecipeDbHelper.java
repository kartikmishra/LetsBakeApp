package com.example.kartikmishra.bakingapp.DatabaseProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kartikmishra.bakingapp.DatabaseProvider.RecipesContract;

public class RecipeDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =1;
    static final String DATABASE_NAME = "recipes.db";


    public RecipeDbHelper (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_RECIPE_TABLE="CREATE TABLE "+RecipesContract.RecipesEntry.TABLE_NAME+ "(" +
                RecipesContract.RecipesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                RecipesContract.RecipesEntry.COLUMN_RECIPES_ID + " INTEGER NOT NULL, "+
                RecipesContract.RecipesEntry.COLUMN_RECIPE_NAME + " VARCHAR NOT NULL, "+
                RecipesContract.RecipesEntry.COLUMN_RECIPE_SERVINGS + " INTEGER NOT NULL, "+
                RecipesContract.RecipesEntry.COLUMN_RECIPE_INGREDIENT + " VARCHAR NOT NULL, "+
                RecipesContract.RecipesEntry.COLUMN_RECIPE_MEASURE + " VARCHAR NOT NULL, "+
                RecipesContract.RecipesEntry.COLUMN_RECIPE_QUANTITY + " INTEGER NOT NULL, "+
                RecipesContract.RecipesEntry.COLUMN_RECIPE_STEP_SHORT_DESCRIPTION + " VARCHAR NOT NULL,"+
                RecipesContract.RecipesEntry.COLUMN_RECIPE_STEP_DESCRIPTION + " VARCHAR NOT NULL, " +
                RecipesContract.RecipesEntry.COLUMN_RECIPE_STEP_VIDEO_URL + " VARCHAR, "+
                RecipesContract.RecipesEntry.COLUMN_RECIPE_STEP_THUMBNAIL_URL + " VARCHAR ); ";

        db.execSQL(SQL_CREATE_RECIPE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ RecipesContract.RecipesEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }
}
