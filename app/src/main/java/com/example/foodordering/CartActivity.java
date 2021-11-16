package com.example.foodordering;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";

    private static DecimalFormat df = new DecimalFormat("0.00");
    private RecyclerView mRecyclerView;
    private CartAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<CartItem> cartList;
    private DBCartHelper mDBCartHelper;
    private DBHistoryHelper mDBHistoryHelper;
    double totalBill = 0.0, finalTotalBill = 0.0, totalPerItem = 0.0, eachPaxBill;
    TextView mTotalBill, mTotalEachPax;
    EditText mTotalPax;
    Button mConfirmOrder;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_gallery);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String username = sharedPreferences.getString(KEY_USERNAME, null);

        //From the
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDBHistoryHelper = new DBHistoryHelper(this);
        mDBCartHelper = new DBCartHelper(this);
        mTotalBill = findViewById(R.id.total_bill);
        mTotalPax = findViewById(R.id.split_pax);
        mTotalEachPax = findViewById(R.id.split_each_pax);
        mConfirmOrder = findViewById(R.id.btn_confirmOrder);
        cartList = new ArrayList<>();

        //Fetching back all data from the database and display it one by one on cart activity
        Cursor cursor = mDBCartHelper.fetchCart();
        if (cursor.getCount() == 0){
            Toast.makeText(CartActivity.this, "No item in the cart! ", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()) {
                int fImage = cursor.getInt(0);
                String fName = cursor.getString(1);
                double fPrice = cursor.getDouble(2);
                int fQuantity = cursor.getInt(3);

                //Count each item quantity
                totalPerItem = fPrice * fQuantity;
                //Add to the total bill
                totalBill = totalBill + totalPerItem;

                //Add to the array list
                cartList.add(new CartItem(fImage, fName, fPrice, fQuantity));
                //Display the total bill on screen
                finalTotalBill = Double.parseDouble(df.format(totalBill));
                mTotalBill.setText(finalTotalBill + "");
            }
            cursor.close();
        }

        //To triggered the confirm button
        mConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalBill != 0) {
                    Boolean status = mDBHistoryHelper.insertData(username, totalBill);

                    mDBCartHelper.deleteAllCart();
                    Intent toConfirmPage = new Intent(getApplicationContext(), PurchaseActivity.class);
                    startActivity(toConfirmPage);
                }else{
                    Toast.makeText(CartActivity.this, "No item in the cart! Try adding food to your cart.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //To calculate and display the total pax amount (unique feature 1)
        mTotalPax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pax = Integer.parseInt(mTotalPax.getText().toString());
                eachPaxBill = totalBill/pax;

                mTotalEachPax.setText(df.format(eachPaxBill));
            }
        });

        //Display the item cart recycler view
        mRecyclerView = findViewById(R.id.recyclerViewCart);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new CartAdapter(cartList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //Get delete food name from adapter and perform delete action
        if (getIntent().hasExtra("message")){
            String msg = getIntent().getStringExtra("message");
            Boolean status = deleteItem(msg);

            if (status = true){
                Intent toRefresh = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(toRefresh);
                Toast.makeText(CartActivity.this, "Item deleted Successfully! ", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(CartActivity.this, "Item deleted failed! ", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //To delete item from the cart list
    public Boolean deleteItem(String deleteName){
        mDBCartHelper.deleteItem(deleteName);
        return true;
    }


}
