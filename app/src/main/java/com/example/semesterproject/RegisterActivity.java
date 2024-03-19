package com.example.semesterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements DBHelper.InsertCallback{

    private EditText emailTextInputRegister,nameTextInputRegister,lastnameTextInputRegister,
            passwordTextInputRegister,passwordTextInputRegister2;
    private Button registerButtonRegister;
    private CheckBox kvkkCheckBox;
    private DBHelper dbHelper;

    @Override
    public void onInsertComplete() {
        Toast.makeText(RegisterActivity.this, "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_tab);

        emailTextInputRegister = findViewById(R.id.emailTextInputRegister);

        nameTextInputRegister = findViewById(R.id.nameTextInputRegister);
        lastnameTextInputRegister = findViewById(R.id.lastnameTextInputRegister);

        passwordTextInputRegister = findViewById(R.id.passwordTextInputRegister);
        passwordTextInputRegister2 = findViewById(R.id.passwordTextInputRegister2);

        kvkkCheckBox = findViewById(R.id.kvkkCheckBox);

        registerButtonRegister = findViewById(R.id.registerButtonRegister);

        dbHelper = new DBHelper(this);

        registerButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTextInputRegister.getText().toString().trim();

                String name = nameTextInputRegister.getText().toString().trim();
                String lastName = lastnameTextInputRegister.getText().toString().trim();

                String password = passwordTextInputRegister.getText().toString().trim();
                String password2 = passwordTextInputRegister2.getText().toString().trim();

                //Check if the user accepted KVKK
                if(!kvkkCheckBox.isChecked()) {
                    Toast.makeText(RegisterActivity.this, "Lütfen KVKK kurallarını kabul ediniz.", Toast.LENGTH_SHORT).show();
                    return; //If not check do not continue
                }

                //Check if the text areas are filled
                if(email.isEmpty() || name.isEmpty() || lastName.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Lütfen bilgilerinizi tam giriniz.", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Check if the both passwords are same
                    if (!password.equals(password2)) {
                        Toast.makeText(RegisterActivity.this, "Şifreler uyuşmuyor. Lütfen tekrar giriniz.", Toast.LENGTH_SHORT).show();
                    }
                    //Check if the given email is valid
                    else if (!dbHelper.isEmailValid(email)) {
                        Toast.makeText(RegisterActivity.this, "Lütfen geçerli bir e-mail giriniz.", Toast.LENGTH_SHORT).show();
                    }
                    else if(!dbHelper.isNameValid(name) || !dbHelper.isNameValid(lastName)) {
                        Toast.makeText(RegisterActivity.this, "Lütfen geçerli bir isim ve soyisim giriniz.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        dbHelper.insertLoginData(email ,name ,lastName, password,RegisterActivity.this,RegisterActivity.this);
                    }
                }
            }
        });
    }
}
