package com.example.foodordering;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "GalleryActivity";
    Button mCartBtn, mIncrease, mDecrease;
    TextView mAmount;
    int amountTracker = 1;
    DBCartHelper DB_cart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_gallery);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCartBtn = findViewById(R.id.gAddToCart);
        mIncrease = findViewById(R.id.gIncrease);
        mDecrease = findViewById(R.id.gDecrease);
        mAmount = findViewById(R.id.gAmount);
        mAmount.setText(Integer.toString(amountTracker));
        DB_cart = new DBCartHelper(this);

        increaseAmount();
        decreaseAmount();

        addToCart();
        getIncomingIntent();
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

    private void increaseAmount(){
        mIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountTracker = amountTracker + 1;
                mAmount.setText(Integer.toString(amountTracker));
            }
        });
    }

    private void decreaseAmount(){
        mDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amountTracker <= 1) {
                    Toast.makeText(GalleryActivity.this, "Minimum order is one item!", Toast.LENGTH_SHORT).show();
                }else{
                    amountTracker = amountTracker - 1;
                    mAmount.setText(Integer.toString(amountTracker));
                }
            }
        });
    }

    private void addToCart(){
        mCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    int foodUrl = getIntent().getIntExtra("food_url", 0);
                    String foodName = getIntent().getStringExtra("food_name");
                    double foodPrice = getIntent().getDoubleExtra("food_price", 0);

                    Boolean checkStatus = DB_cart.checkDuplicate(foodName);
                    if (checkStatus == true){
                        //update existing record
                        Boolean checkDBStatus = DB_cart.updateDuplicate(foodUrl, foodName, foodPrice, amountTracker);

                        if (checkDBStatus == true) {
                            Toast.makeText(GalleryActivity.this, "Cart updated successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(GalleryActivity.this, "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        //insert as a new record
                        Boolean insertCart = DB_cart.insertCart(foodUrl, foodName, foodPrice, amountTracker);

                        if (insertCart == true) {
                            Toast.makeText(GalleryActivity.this, "Add to Cart Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
            }
        });
    }

    //Get the incoming intent from the main activity food item selected
    private void getIncomingIntent(){
        if (getIntent().hasExtra("food_url") && getIntent().hasExtra("food_name") && getIntent().hasExtra("food_price")){
            //Get all the information from Food Adapter and display it
            int foodUrl = getIntent().getIntExtra("food_url", 0);
            String foodName = getIntent().getStringExtra("food_name");
            double foodPrice = getIntent().getDoubleExtra("food_price",0);

            setImage(foodUrl, foodName, foodPrice);
        }
    }

    //Using the intent information and display it on the gallery activity
    private void setImage(int foodUrl, String foodName, double foodPrice) {
        //Take all the information and display on the gallery view
        TextView name = findViewById(R.id.gFoodName);
        name.setText(foodName);

        TextView price = findViewById(R.id.gFoodPrice);
        price.setText(Double.toString(foodPrice));

        ImageView image = findViewById(R.id.gFoodImage);
        image.setImageResource(foodUrl);
    }
}
