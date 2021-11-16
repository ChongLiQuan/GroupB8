package com.example.foodordering;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private ArrayList<FoodItem> mFoodList;

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mFoodName;
        public TextView mPrice;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mFoodName = itemView.findViewById(R.id.foodName);
            mPrice = itemView.findViewById(R.id.foodPrice);
        }
    }

    public FoodAdapter(ArrayList<FoodItem> foodList){
        mFoodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Set the layout with each individual food item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        FoodViewHolder foodHolder = new FoodViewHolder(view);
        return foodHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem currentItem = mFoodList.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mFoodName.setText(currentItem.getItemName());
        holder.mPrice.setText(Double.toString(currentItem.getPrice()));

        //Clickable card view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //When one item is clicked send current information to the Gallery Activity to display
                Intent intent = new Intent(v.getContext(), GalleryActivity.class);
                intent.putExtra("food_url", currentItem.getImageResource());
                intent.putExtra("food_name", currentItem.getItemName());
                intent.putExtra("food_price", currentItem.getPrice());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }

    public void filterList(ArrayList<FoodItem> filteredList){
        mFoodList = filteredList;
        notifyDataSetChanged();
    }
}
