package com.example.foodordering;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.ParseException;

public class TipsActivity extends AppCompatActivity {
    private static DecimalFormat df = new DecimalFormat("0.00");
    EditText mTotalBill, mTaxPercentage;
    Button btnCalculate;
    TextView mTotalTips;
    double mBill, mPercentage;
    double mTotal = 0.00, mTotal_Format;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tips_calculator);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mTotalBill = findViewById(R.id.et_totalBill);
        mTaxPercentage = findViewById(R.id.et_totalPercentage);
        mTotalTips = findViewById(R.id.et_totalTips);
        btnCalculate = findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBill = Double.parseDouble(mTotalBill.getText().toString());
                mPercentage = Double.parseDouble(mTaxPercentage.getText().toString());

                mTotal = (mBill * (mPercentage/100.00));
                mTotal_Format = Double.parseDouble(df.format(mTotal));

                mTotalTips.setText(mTotal_Format+"");
            }
        });
    }
}
