package com.example.kartikmishra.bakingapp.RecipeSteps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kartikmishra.bakingapp.R;
import com.example.kartikmishra.bakingapp.RecipeDetails.RecipeDetailFragmentMasterList;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsAdapterViewHolder> {


    private Context mContext;
    private ListItemClickListener mListItemClickListener;
    private List<Steps> mListSteps = new ArrayList<>();
    private TextView steps_tv;
    private ImageView recipe_step_iv;

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

        if(RecipeDetailFragmentMasterList.stepsList!=null){
            if(RecipeDetailFragmentMasterList.stepsList.size()>0 ){
                steps_tv.setText(RecipeDetailFragmentMasterList.stepsList.get(position).getShortDescription());
                //recipe_step_iv.setImageResource(R.drawable.chocolatecake);
            }

        }

        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return mListSteps.size();
    }

    public class RecipeStepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public RecipeStepsAdapterViewHolder(View itemView) {
            super(itemView);

            steps_tv = itemView.findViewById(R.id.recipe_step_textView);
            //recipe_step_iv = itemView.findViewById(R.id.recipe_step_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mListItemClickListener.onListItemClick(clickedPosition);
        }
    }
}
