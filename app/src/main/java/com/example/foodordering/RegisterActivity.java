package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    // defining our own password pattern
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");   // defining our own password pattern

    //Sign In and Sign Up Variables Declaration
    EditText mUsername, mPassword, mConfirmPassword, mAddress;
    Button mSignUpButton, mSignInButton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        //Sign In and Sign Up Variables Declaration
        mUsername = (EditText) findViewById(R.id.signup_username);
        mPassword = (EditText) findViewById(R.id.signup_password);
        mConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        mAddress = (EditText) findViewById(R.id.signup_address);
        mSignUpButton = (Button) findViewById(R.id.btn_sign_up_1);
        mSignInButton = (Button) findViewById(R.id.btn_sign_in_1);
        DB = new DBHelper(this);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mUsername.getText().toString();
                String pass = mPassword.getText().toString();
                String confirmPass = mConfirmPassword.getText().toString();
                String address = mAddress.getText().toString();


                //Check if any sign up fields is empty
                if (user.equals("") || pass.equals("") || confirmPass.equals("") || address.equals("")){
                    Toast.makeText(RegisterActivity.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                }else if (!PASSWORD_PATTERN.matcher(pass).matches()) {
                    //Check password pattern
                    Toast.makeText(RegisterActivity.this, "Password are too weak!", Toast.LENGTH_SHORT).show();
                }else{ //Else

                    if (pass.equals(confirmPass)){ //check password same as confirm password
                        Boolean checkUser = DB.checkUsername(user);
                        if (checkUser == false){ //try to insert and check if username is taken
                            Boolean insert = DB.insertData(user, pass, address);
                            if (insert == true){
                                Toast.makeText(RegisterActivity.this, "Account register successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this, "Account register failed!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this, "User already exists! Please sign in.", Toast.LENGTH_SHORT).show();

                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Password and Confirm Password is not match!", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

        //Bring user to the login activity
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}