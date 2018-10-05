package com.example.kartikmishra.bakingapp.RecipeSteps;

import android.os.AsyncTask;

import com.example.kartikmishra.bakingapp.RecipeDetails.RecipeDetailFragmentMasterList;
import com.example.kartikmishra.bakingapp.Recipes.FetchRecipesAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchStepsAsyncTask extends AsyncTask<String,Void,List<Steps>> {


    private int i;
    private OnTaskCompleted onTaskCompleted;

    public FetchStepsAsyncTask(OnTaskCompleted applicationContext) {
        this.onTaskCompleted = applicationContext;
    }

    public interface OnTaskCompleted{
        void onStepsTaskCompleted(List<Steps> steps);
    }



    private List<Steps> getDataFromJson(String jsonStr) throws JSONException{


        RecipeDetailFragmentMasterList.stepsList.clear();
        RecipeDetailFragmentMasterList.ids.clear();
        RecipeDetailFragmentMasterList.shortDescription.clear();
        RecipeDetailFragmentMasterList.description.clear();
        RecipeDetailFragmentMasterList.videoURLs.clear();
        RecipeDetailFragmentMasterList.thumbnailURLs.clear();
        JSONArray recipesArray = new JSONArray(jsonStr);

        List<Steps> result = new ArrayList<>();

        JSONArray steps = recipesArray.getJSONObject(i).getJSONArray("steps");

        for(int i=0 ;i<steps.length();i++){

            JSONObject jsonStepsOb = steps.getJSONObject(i);
            Steps stepModel = new Steps();

            stepModel.setId(jsonStepsOb.getInt("id"));
            stepModel.setShortDescription(jsonStepsOb.getString("shortDescription"));
            stepModel.setDescription(jsonStepsOb.getString("description"));

            RecipeDetailFragmentMasterList.ids.add(jsonStepsOb.getInt("id"));
            RecipeDetailFragmentMasterList.shortDescription.add(jsonStepsOb.getString("shortDescription"));
            RecipeDetailFragmentMasterList.description.add(jsonStepsOb.getString("description"));
            if(jsonStepsOb.getString("videoURL")!=null){

                RecipeDetailFragmentMasterList.videoURLs.add(jsonStepsOb.getString("videoURL"));

                stepModel.setVideoUrl(jsonStepsOb.getString("videoURL"));
            }
            else {
                RecipeDetailFragmentMasterList.videoURLs.add("");
                stepModel.setVideoUrl("");

            }

            if(jsonStepsOb.getString("thumbnailURL")!=null){

                RecipeDetailFragmentMasterList.thumbnailURLs.add(jsonStepsOb.getString("thumbnailURL"));
                stepModel.setThumbNailUrl(jsonStepsOb.getString("thumbnailURL"));

            }
            else {
                RecipeDetailFragmentMasterList.thumbnailURLs.add("");
                stepModel.setVideoUrl("");
            }


            result.add(stepModel);
            RecipeDetailFragmentMasterList.stepsList.add(stepModel);


        }

        return result;

    }



    @Override
    protected List<Steps> doInBackground(String... strings) {
        if(strings.length == 0){
            return null;
        }

        try {

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

            return getDataFromJson(FetchRecipesAsyncTask.jsonStr);
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Steps> steps) {
        if(steps!=null){
            if(steps.size()>0){


                RecipeDetailFragmentMasterList.ids.get(0);
                //RecipeDetailFragmentMasterList.shortDescription.get(0);
                RecipeDetailFragmentMasterList.description.get(0);
                RecipeDetailFragmentMasterList.videoURLs.get(0);
                RecipeDetailFragmentMasterList.thumbnailURLs.get(0);
                onTaskCompleted.onStepsTaskCompleted(steps);
            }
        }

    }
}
