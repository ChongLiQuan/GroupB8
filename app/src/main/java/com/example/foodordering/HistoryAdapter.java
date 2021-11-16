package com.example.foodordering;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private ArrayList<HistoryItem> mHistoryList;

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView mBill;
        public TextView mDate;


        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mBill = itemView.findViewById(R.id.history_date);
            mDate = itemView.findViewById(R.id.history_bill);
        }
    }

    public HistoryAdapter(ArrayList<HistoryItem> historyList){
        mHistoryList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        HistoryViewHolder historyHolder = new HistoryViewHolder(view);
        return historyHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryItem currentItem = mHistoryList.get(position);
        holder.mBill.setText("RM: " + currentItem.getOrderBill());
        holder.mDate.setText(currentItem.getOrderDate());
    }

    @Override
    public int getItemCount() {
        return mHistoryList.size();
    }
}
