package com.example.kartikmishra.bakingapp.RecipeIngredients;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kartikmishra.bakingapp.R;
import com.example.kartikmishra.bakingapp.RecipeDetails.RecipeDetailFragmentMasterList;

import java.util.List;

public class RecipeDetailIngredientsAdapter extends RecyclerView.Adapter<RecipeDetailIngredientsAdapter.RecipeDetailIngredientViewHolder> {

    private static final String TAG = "RecipeDetailIngredients";

    private TextView ingredient_tv;
    private TextView measure_tv;
    private TextView quantity_tv;
    private List<Ingredients> ingredientsList;


    private Context context;

    public RecipeDetailIngredientsAdapter(Context context, List<Ingredients> mIngredientsList) {
        this.context = context;
        this.ingredientsList = mIngredientsList;
    }

    public RecipeDetailIngredientsAdapter() {

    }

    @NonNull
    @Override
    public RecipeDetailIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.recipe_detail_ingredients_item,parent,false);

        return new RecipeDetailIngredientViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailIngredientViewHolder holder, int position) {

        ingredient_tv.setText("Ingredient: "+RecipeDetailFragmentMasterList.ingredient.get(position));
        measure_tv.setText("Measure: "+RecipeDetailFragmentMasterList.measure.get(position));
        quantity_tv.setText("Quantity: "+RecipeDetailFragmentMasterList.quantity.get(position));

        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    class RecipeDetailIngredientViewHolder extends RecyclerView.ViewHolder {
       public RecipeDetailIngredientViewHolder(View itemView) {
           super(itemView);

           ingredient_tv = itemView.findViewById(R.id.recipe_detail_ingredient_text_view);
           measure_tv = itemView.findViewById(R.id.recipe_detail_ingredient_measure_text_view);
           quantity_tv = itemView.findViewById(R.id.recipe_detail_ingredient_quantity_text_view);
       }
   }
}
