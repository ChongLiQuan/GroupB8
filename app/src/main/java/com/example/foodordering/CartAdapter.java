package com.example.foodordering;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private ArrayList<CartItem> mCartList;

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mFoodName;
        public TextView mPrice;
        public TextView mQuantity;
        public Button mDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.cart_image);
            mFoodName = itemView.findViewById(R.id.cart_name);
            mPrice = itemView.findViewById(R.id.cart_price);
            mQuantity = itemView.findViewById(R.id.cart_amount);
            mDelete = itemView.findViewById(R.id.delete_button);
        }
    }

    public CartAdapter(ArrayList<CartItem> cartList){
        mCartList = cartList;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        CartViewHolder cartHolder = new CartViewHolder(view);
        return cartHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        CartItem currentItem = mCartList.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mFoodName.setText(currentItem.getItemName());
        holder.mPrice.setText(Double.toString(currentItem.getPrice()));
        holder.mQuantity.setText(Integer.toString(currentItem.getQuantity()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GalleryActivity.class);
                intent.putExtra("food_url", currentItem.getImageResource());
                intent.putExtra("food_name", currentItem.getItemName());
                intent.putExtra("food_price", currentItem.getPrice());
                intent.putExtra("food_quantity", currentItem.getQuantity());
                v.getContext().startActivity(intent);
            }
        });

        //On click button for each of the item on cart list
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the current item name
                String deleteName = currentItem.getItemName();
                //Send it to Card Activity to delete
                Intent intent = new Intent(v.getContext(), CartActivity.class);
                intent.putExtra("message", deleteName);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }


}
