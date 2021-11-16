package com.example.foodordering;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";

    private static DecimalFormat df = new DecimalFormat("0.00");
    private RecyclerView mRecyclerView;
    private HistoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<HistoryItem> historyList;
    private DBHistoryHelper mDBHistoryHelper;
    TextView mBill, mDate;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String testUser = sharedPreferences.getString(KEY_USERNAME, null);

        mDBHistoryHelper = new DBHistoryHelper(this);
        mBill = findViewById(R.id.history_bill);
        mDate = findViewById(R.id.history_date);
        historyList = new ArrayList<>();

        //Fetching back all data from the database and display it one by one on cart activity
        Cursor cursor = mDBHistoryHelper.fetchHistory(testUser);
        if (cursor.getCount() == 0){
            Toast.makeText(HistoryActivity.this, "No past order record found! Try to place a new order. ", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                double bill = cursor.getDouble(2);
                String date = cursor.getString(3);

                Double finalTotalBill = Double.parseDouble(df.format(bill));
                historyList.add(new HistoryItem(name, finalTotalBill, date));
            }
            cursor.close();
        }

        buildRecyclerView();
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerViewHistory);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new HistoryAdapter(historyList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
