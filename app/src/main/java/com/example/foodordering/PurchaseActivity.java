package com.example.foodordering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PurchaseActivity extends AppCompatActivity {

    Button mBtn_Confirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        mBtn_Confirm = findViewById(R.id.btn_confirm);

        mBtn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainPage = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(toMainPage);
            }
        });

    }
}
