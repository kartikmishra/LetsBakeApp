package com.example.kartikmishra.bakingapp.Recipes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kartikmishra.bakingapp.R;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> {

    private static final String TAG = "RecipesAdapter";

    Context mContext;
    private TextView nameTextView;
    private TextView servingsTv;
    private ImageView recipesImageView;
    public static ImageView fav_symbol_iv;
    public static int listPosition;
    private List<RecipesModel> mRecipesList;
    private  ListItemClickListener mListItemClickListener;


    public RecipesAdapter(Context mContext,List<RecipesModel> recipesList,ListItemClickListener listItemClickListener) {
        this.mContext = mContext;
        this.mRecipesList = recipesList;
        this.mListItemClickListener = listItemClickListener;
    }

    public RecipesAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);

    }

    @NonNull
    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.recipes_recyclerview_item,parent,false);

        return new RecipesAdapterViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecipesAdapterViewHolder holder, final int position) {

        //RecipesFragment.recipesList.clear();
        if(RecipesFragment.recipesList.size()>0){
            nameTextView.setText(RecipesFragment.recipesList.get(position).getName());
            servingsTv.setText("Servings: "+RecipesFragment.recipesList.get(position).getServings()+" persons");

            if(RecipesFragment.recipesList.get(position).getId()==1){
                recipesImageView.setImageResource(R.drawable.nutellapie);
            }
            else if(RecipesFragment.recipesList.get(position).getId()==2){
                recipesImageView.setImageResource(R.drawable.brownies);
            }
            else if(RecipesFragment.recipesList.get(position).getId()==3){
                recipesImageView.setImageResource(R.drawable.yellowcake);
            }
            else if(RecipesFragment.recipesList.get(position).getId()==4){
                recipesImageView.setImageResource(R.drawable.cheesecake);
            }



        }

        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return mRecipesList.size();
    }

   public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        RecipesAdapterViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.recipesActivity_recipeName_textView);
            servingsTv = itemView.findViewById(R.id.recipes_servings_tv);
            recipesImageView = itemView.findViewById(R.id.recipes_imageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mListItemClickListener.onListItemClick(clickedPosition);


        }
    }
}
