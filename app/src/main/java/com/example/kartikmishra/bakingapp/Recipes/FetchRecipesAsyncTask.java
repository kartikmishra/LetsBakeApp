package com.example.kartikmishra.bakingapp.Recipes;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.kartikmishra.bakingapp.HttpHandler;
import com.example.kartikmishra.bakingapp.Recipes.RecipesFragment;
import com.example.kartikmishra.bakingapp.Recipes.RecipesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchRecipesAsyncTask extends AsyncTask<String,Void,List<RecipesModel>> {

    private static final String TAG = "FetchRecipesAsyncTask";


    private OnTaskCompleted taskCompleted;

    public static String jsonStr = null;


    public FetchRecipesAsyncTask(OnTaskCompleted applicationContext) {
        this.taskCompleted = applicationContext;
    }




    public interface OnTaskCompleted{
        void onRecipesTaskCompleted(List<RecipesModel> recipes);
    }

    private List<RecipesModel> getDataFromJson(String jsonStr) throws JSONException{


        RecipesFragment.recipesList.clear();
        RecipesFragment.recipesName.clear();
        JSONArray recipesArray = new JSONArray(jsonStr);



        List<RecipesModel> results = new ArrayList<>();

        for(int i=0;i<recipesArray.length();i++){
            JSONObject recipe = recipesArray.getJSONObject(i);
            RecipesModel recipesModel = new RecipesModel();
            recipesModel.setId(recipe.getInt("id"));
            recipesModel.setName(recipe.getString("name"));
            recipesModel.setServings(recipe.getInt("servings"));

            results.add(recipesModel);
            RecipesFragment.recipesList.add(recipesModel);

        }



        return results;
    }

    @Override
    protected List<RecipesModel> doInBackground(String... strings) {
        if(strings.length==0){
            return null;
        }

        try {

            Uri uri = Uri.parse(strings[0]).buildUpon().build();
            String response = getJsonString(uri);

            return getDataFromJson(response);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private String getJsonString(Uri uri) {


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
    protected void onPostExecute(List<RecipesModel> recipesModels) {
        if(recipesModels!=null){
            if(recipesModels.size()>0){

                taskCompleted.onRecipesTaskCompleted(recipesModels);

                RecipesFragment.recipesName.add(recipesModels.get(0).getName());
            }
        }
    }
}
