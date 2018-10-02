package com.example.kartikmishra.bakingapp.DatabaseProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class RecipeContentProvider extends ContentProvider {

    public static final int RECIPES = 100;
    private RecipeDbHelper mRecipeDbHelper;


    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(RecipesContract.CONTENT_AUTHORITY,RecipesContract.PATH_RECIPES,RECIPES);
        return uriMatcher;
    }
    @Override
    public boolean onCreate() {
        Context context = getContext();
        mRecipeDbHelper = new RecipeDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mRecipeDbHelper.getReadableDatabase();

        Cursor retCursor;

        switch (buildUriMatcher().match(uri)){
            case RECIPES:
                retCursor = db.query(RecipesContract.RecipesEntry.TABLE_NAME,null,
                        null,null,null,null,null);

                break;
                default:
                    throw new UnsupportedOperationException();
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = buildUriMatcher().match(uri);

        switch (match){
            case RECIPES:
                return RecipesContract.RecipesEntry.CONTENT_TYPE;
                default:
                    throw  new UnsupportedOperationException("Unknown uri: "+uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();

        Uri retUri;
        switch (buildUriMatcher().match(uri)){
            case RECIPES:

                long _id = db.insert(RecipesContract.RecipesEntry.TABLE_NAME,null,values);
                if(_id>0){
                    retUri =  ContentUris.withAppendedId(RecipesContract.RecipesEntry.CONTENT_URI,_id);
                }else {
                    throw new android.database.SQLException("Failed to insert row int :"+uri);
                }
                break;
                default:
                    throw new UnsupportedOperationException();
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return retUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();
        int rows_deleted;
        if(null == selection) selection="1";

        switch (buildUriMatcher().match(uri)){
            case RECIPES:
                rows_deleted = db.delete(RecipesContract.RecipesEntry.TABLE_NAME,selection,selectionArgs);
                break;
                default:
                    throw new UnsupportedOperationException();
        }

        if (rows_deleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows_deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();

        int rows_updated;
        switch (buildUriMatcher().match(uri)){
            case RECIPES:
                rows_updated = db.update(RecipesContract.RecipesEntry.TABLE_NAME,values,selection,
                        selectionArgs);
                break;
                default:
                    throw new UnsupportedOperationException();
        }

        if(rows_updated!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rows_updated;
    }
}
