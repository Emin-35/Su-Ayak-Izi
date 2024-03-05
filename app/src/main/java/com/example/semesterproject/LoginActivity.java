package com.example.semesterproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText emailTextInput,passwordTextInput;
    private Button forgotPasswordButton,loginButton,registerButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_tab);

        emailTextInput = findViewById(R.id.emailTextInput);
        passwordTextInput = findViewById(R.id.passwordTextInput);

        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        dbHelper = new DBHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTextInput.getText().toString();
                String password = passwordTextInput.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()) {
                    boolean status = dbHelper.checkLoginStatus(email,password);

                    //If login successful, log-in the user to system
                    if(status) {
                        Toast.makeText(LoginActivity.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, TranslationActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Girilen Bilgiler Hatalı veya Yanlış", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Lütfen E-posta ve Şifre Giriniz", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //If user clicks on the register button, move him/her to register page
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}