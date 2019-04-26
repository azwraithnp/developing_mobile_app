package com.example.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recyclerview.model.Food;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {

    private RecyclerView rvFoods;
    private LayoutInflater mInflater;
    private List<Food> lFoods;
    private Context mcontext;

    public  FoodListAdapter(Context context, List<Food> foodlst){
        mInflater = LayoutInflater.from(context);
        this.lFoods = foodlst;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public FoodListAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mItemView = mInflater.inflate(R.layout.activity_recipe_list_item,viewGroup,false);
        return new FoodViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.FoodViewHolder foodViewHolder, int position) {
        Food food = lFoods.get(position);
        foodViewHolder.foodTitle.setText(food.getTitle());
        foodViewHolder.foodDesc.setText(food.getDescription());
    }

    @Override
    public int getItemCount() {
        return lFoods.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final FoodListAdapter mAdapter;
        public TextView foodTitle;
        public TextView foodDesc;

        public FoodViewHolder(@NonNull View itemView, FoodListAdapter adapter) {
            super(itemView);
            foodTitle = itemView.findViewById(R.id.foodTitle);
            foodDesc = itemView.findViewById(R.id.foodContent);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Food item = lFoods.get(getAdapterPosition());

            Intent intent = new Intent(mcontext,FoodDetailActivity.class);
            intent.putExtra("Food",item);
            mcontext.startActivity(intent);
        }
    }
}
