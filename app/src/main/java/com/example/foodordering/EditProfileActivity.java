package com.example.foodordering;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";

    TextView mUsername;
    EditText mAddress;
    Button btnUpdate, btnBack;

    private DBHelper mDBHelper;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String username = sharedPreferences.getString(KEY_USERNAME, null);
        mUsername = (TextView) findViewById(R.id.display_username);
        mAddress = (EditText) findViewById(R.id.display_address);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnBack = (Button) findViewById(R.id.btn_backProfile);
        mDBHelper = new DBHelper(this);

        Cursor cursor = mDBHelper.fetchUser(username);

        String fUsername = "";

        if (cursor.getCount() == 0) {
            Toast.makeText(EditProfileActivity.this, "No user found! ", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                fUsername = cursor.getString(0);

                mUsername.setText(fUsername);
            }
        }

        String finalFUsername = fUsername;
        Toast.makeText(EditProfileActivity.this, fUsername, Toast.LENGTH_SHORT).show();

        // When Update Button is Clicked
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newAddress = mAddress.getText().toString();

                // Check if input is empty
                if (newAddress.equals("")) {
                    Toast.makeText(EditProfileActivity.this, "Address cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    // Update Address
                    boolean updated = mDBHelper.updateData(finalFUsername, newAddress);
                    if (updated) Toast.makeText(EditProfileActivity.this, "Update Successful! ", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(EditProfileActivity.this, "Update Not Success", Toast.LENGTH_SHORT).show();
                }
                Intent toProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(toProfile);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(toProfile);
            }
        });
    }
}
