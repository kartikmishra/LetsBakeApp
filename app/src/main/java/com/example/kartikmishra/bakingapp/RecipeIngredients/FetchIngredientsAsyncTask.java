package com.example.kartikmishra.bakingapp.RecipeIngredients;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.kartikmishra.bakingapp.HttpHandler;
import com.example.kartikmishra.bakingapp.RecipeDetails.RecipeDetailFragmentMasterList;
import com.example.kartikmishra.bakingapp.Recipes.FetchRecipesAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchIngredientsAsyncTask extends AsyncTask<String,Void,List<Ingredients>> {

    private static final String TAG = "FetchIngredientsAsyncTask";

    OnTaskCompleted taskCompleted;
    int i;

    public FetchIngredientsAsyncTask(OnTaskCompleted applicationTask){
        this.taskCompleted = applicationTask;
    }

    public interface OnTaskCompleted{
        void onIngredientsTaskCompleted(List<Ingredients> ingredients);
    }


    private List<Ingredients> getDataFromJson(String jsonStr) throws JSONException{


        RecipeDetailFragmentMasterList.ingredientsList.clear();
        RecipeDetailFragmentMasterList.measure.clear();
        RecipeDetailFragmentMasterList.ingredient.clear();
        RecipeDetailFragmentMasterList.quantity.clear();

        JSONArray recipesArray = new JSONArray(jsonStr);

        List<Ingredients> results = new ArrayList<>();

//        for(int i=0;i<recipesArray.length();i++){

            JSONArray ingredient = recipesArray.getJSONObject(i).getJSONArray("ingredients");



            for(int j=0;j<ingredient.length();j++){
                JSONObject ingredientOb = ingredient.getJSONObject(j);
                Ingredients ingredients  = new Ingredients();;

                ingredients.setIngredient(ingredientOb.getString("ingredient"));
                ingredients.setMeasure(ingredientOb.getString("measure"));
                ingredients.setQuantity(ingredientOb.getInt("quantity"));

                RecipeDetailFragmentMasterList.ingredient.add(ingredientOb.getString("ingredient"));
                RecipeDetailFragmentMasterList.measure.add(ingredientOb.getString("measure"));
                RecipeDetailFragmentMasterList.quantity.add(ingredientOb.getString("quantity"));

                results.add(ingredients);
                RecipeDetailFragmentMasterList.ingredientsList.add(ingredients);


            }


       // }



        return results;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected List<Ingredients> doInBackground(String... strings) {

        if(strings.length==0){
            return null;
        }


        try {
//
//            Uri uri = Uri.parse(strings[0]).buildUpon().build();
//            String response = getJsonString(uri);
            if(strings[0].contains(String.valueOf(0))){
                i=0;
            }
            else if(strings[0].contains(String.valueOf(1))){
                i=1;
            }
            else if(strings[0].contains(String.valueOf(2))){
                i=2;
            }
                else if(strings[0].contains(String.valueOf(3))){
                i=3;
            }

            Log.d(TAG, "doInBackground: value of i:"+i);
            return getDataFromJson(FetchRecipesAsyncTask.jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }


    private String getJsonString(Uri uri) {
        String jsonStr = null;

        try {
            String recipeString = uri.toString();
            HttpHandler sh = new HttpHandler();
            jsonStr = sh.makeServiceCall(recipeString);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonStr;
    }

    @Override
    protected void onPostExecute(List<Ingredients> ingredients) {
        if(ingredients!=null){
            if(ingredients.size()>0){

                RecipeDetailFragmentMasterList.ingredient.get(0);
                RecipeDetailFragmentMasterList.measure.get(0);
                RecipeDetailFragmentMasterList.quantity.get(0);
                taskCompleted.onIngredientsTaskCompleted(ingredients);

            }
        }
    }
}
