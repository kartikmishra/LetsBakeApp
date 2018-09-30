package com.example.kartikmishra.bakingapp.RecipeSteps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kartikmishra.bakingapp.R;
import com.example.kartikmishra.bakingapp.RecipeDetails.RecipeDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsAdapterViewHolder> {


    private Context mContext;
    private ListItemClickListener mListItemClickListener;
    private List<Steps> mListSteps = new ArrayList<>();
    private TextView steps_tv;

    public RecipeStepsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public RecipeStepsAdapter(Context context,List<Steps> listSteps, ListItemClickListener listItemClickListener) {
        this.mContext = context;
        this.mListSteps = listSteps;
        this.mListItemClickListener = listItemClickListener;
    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public RecipeStepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_detail_step_item,parent,false);
        return new RecipeStepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsAdapterViewHolder holder, int position) {

        if(RecipeDetailFragment.stepsList!=null){
            if(RecipeDetailFragment.stepsList.size()>0 ){
                steps_tv.setText(RecipeDetailFragment.stepsList.get(position).getShortDescription());
            }

        }

        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return mListSteps.size();
    }

    class RecipeStepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public RecipeStepsAdapterViewHolder(View itemView) {
            super(itemView);

            steps_tv = itemView.findViewById(R.id.recipe_step_textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mListItemClickListener.onListItemClick(clickedPosition);
        }
    }
}
