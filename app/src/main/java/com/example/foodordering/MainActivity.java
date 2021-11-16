package com.example.foodordering;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    ImageView mCate1, mCate2, mCate3, mCate4, mCate5, mCate6;
    private EditText mSearchBar;
    private RecyclerView mRecyclerView;
    private FoodAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<FoodItem> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Categories Declaration
        mCate1 = findViewById(R.id.cate1);
        mCate2 = findViewById(R.id.cate2);
        mCate3 = findViewById(R.id.cate3);
        mCate4 = findViewById(R.id.cate4);
        mCate5 = findViewById(R.id.cate5);
        mCate6 = findViewById(R.id.cate6);

        //Categories
        Category_All();
        Category_FastFood();
        Category_Noodle();
        Category_Dessert();
        Category_Drink();
        Category_Bread();

        searchBar(); //Apply Search Bar
        createFoodList();
        buildRecyclerView();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_cart:
                Intent toCart = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(toCart);
                return true;

            case R.id.menu_profile:
                Intent toProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(toProfile);
                return true;

            case R.id.menu_history:
                Intent toHistory = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(toHistory);
                return true;

            case R.id.menu_tips:
                Intent toCal = new Intent(getApplicationContext(), TipsActivity.class);
                startActivity(toCal);
                return true;

            case R.id.menu_logout:
                Intent toLogout = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(toLogout);
                Toast.makeText(this, "Thank you for choosing us! Bye bye!", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //Category Main
    private void Category_All(){
        mCate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<FoodItem> allList = new ArrayList<>();

                for (FoodItem item: foodList){
                        allList.add(item);
                }
                mAdapter.filterList(allList);
            }
        });
    }

    private void Category_FastFood(){
        mCate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<FoodItem> burgerList = new ArrayList<>();

                for (FoodItem item: foodList){
                    if (item.getCategory().toLowerCase().contains("fastfood")) {
                        burgerList.add(item);
                    }
                }
                mAdapter.filterList(burgerList);
            }
        });
    }

    private void Category_Noodle(){
        mCate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<FoodItem> noodleList = new ArrayList<>();

                for (FoodItem item: foodList){
                    if (item.getCategory().toLowerCase().contains("noodle")) {
                        noodleList.add(item);
                    }
                }
                mAdapter.filterList(noodleList);
            }
        });
    }

    private void Category_Dessert(){
        mCate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<FoodItem> dessertList = new ArrayList<>();

                for (FoodItem item: foodList){
                    if (item.getCategory().toLowerCase().contains("dessert")) {
                        dessertList.add(item);
                    }
                }
                mAdapter.filterList(dessertList);
            }
        });
    }

    private void Category_Drink(){
        mCate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<FoodItem> drinkList = new ArrayList<>();

                for (FoodItem item: foodList){
                    if (item.getCategory().toLowerCase().contains("drink")) {
                        drinkList.add(item);
                    }
                }
                mAdapter.filterList(drinkList);
            }
        });
    }

    private void Category_Bread(){
        mCate6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<FoodItem> breadList = new ArrayList<>();

                for (FoodItem item: foodList){
                    if (item.getCategory().toLowerCase().contains("bread")) {
                        breadList.add(item);
                    }
                }
                mAdapter.filterList(breadList);
            }
        });
    }

    //Apply search bar
    private void searchBar(){
        mSearchBar = findViewById(R.id.et_search);
        mSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            } //Take the filtered keyword from the function
        });
    }

    //Filter keyword and add into new list
    private void filter(String text){
        ArrayList<FoodItem> filteredList = new ArrayList<>();

        for (FoodItem item: foodList){
            if (item.getItemName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    //Create example food list
    private void createFoodList() {
        foodList = new ArrayList<>();
        foodList.add(new FoodItem(R.drawable.friedchicken, "Fried Chicken", "fastfood", 12.99));
        foodList.add(new FoodItem(R.drawable.friednoodle, "Fried Noodle", "noodle",8.99));
        foodList.add(new FoodItem(R.drawable.burger, "Chicken Burger", "fastfood",16.80));
        foodList.add(new FoodItem(R.drawable.pizza, "Pizza", "fastfood",21.50));
        foodList.add(new FoodItem(R.drawable.cake1, "Chocolate Cake ", "dessert",154.50));
        foodList.add(new FoodItem(R.drawable.drink1, "Lemon Sparkling", "drink",13.50));
        foodList.add(new FoodItem(R.drawable.burger2, "Beef Burger", "fastfood",18.50));
    }

    //Setting up the food recycler view
    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerViewMain);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new FoodAdapter(foodList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

}
