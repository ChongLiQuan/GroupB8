package com.example.foodordering;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";

    TextView mUsername, mAddress;
    Button btnEdit, btnBack;
    private DBHelper mDBHelper;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String testUser = sharedPreferences.getString(KEY_USERNAME, null);

        mUsername = (TextView) findViewById(R.id.display_username);
        mAddress = (TextView) findViewById(R.id.display_address);
        btnEdit = (Button) findViewById(R.id.btn_edit);
        btnBack = (Button) findViewById(R.id.btn_backHome);
        mDBHelper = new DBHelper(this);

        //Fetching back all data from the database
        Cursor cursor = mDBHelper.fetchUser(testUser);

        if (cursor.getCount() == 0) {
            Toast.makeText(ProfileActivity.this, "No user found! ", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String fUsername = cursor.getString(0);
                String fAddress = cursor.getString(2);

                mUsername.setText(fUsername);
                mAddress.setText(fAddress);
            }
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toEdit = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(toEdit);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHome = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(toHome);
            }
        });
    }
}
