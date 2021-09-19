package com.example.todoapp;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.todoapp.adapter.RecyclerViewAdapter;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText etEmail, etPassword;
    private TextInputLayout tilEmail, tilPassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.button);
        etEmail = findViewById(R.id.editTextTextEmailAddress);
        etPassword = findViewById(R.id.editTextTextPassword);
        tilEmail = findViewById(R.id.emailTextInputLayout);
        tilPassword = findViewById(R.id.passwordTextInputLayout);

        etEmail.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);


        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidEmail(etEmail.getText())) {
                    tilEmail.setError("Please enter valid email address.");
                } else {
                    tilEmail.setError(null);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidPassword(etPassword.getText().toString().trim())) {
                    tilPassword.setError("Please enter valid password.");
                } else {
                    tilPassword.setError(null);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidEmail(etEmail.getText()) && isValidPassword(etPassword.getText().toString().trim())) {
                    startActivity(new Intent(MainActivity.this, TodoListViewActivity.class));
                } else if (!isValidEmail(etEmail.getText())) {
                    tilEmail.setError("Please enter valid email address.");
                } else if (!isValidPassword(etPassword.getText().toString().trim())) {
                    tilPassword.setError("Please enter valid password.");
                }
            }
        });
    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isValidPassword(CharSequence input) {
        return Pattern.compile("^[0-9]{6}$").matcher(input).matches();
    }

}