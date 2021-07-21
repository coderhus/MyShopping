package com.example.myshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView register_txt;
    EditText password_edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AnhXa();

        // click
        register_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        password_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password_edt.getTransformationMethod().getClass().getSimpleName() .equals("PasswordTransformationMethod")) {
                    password_edt.setTransformationMethod(new SingleLineTransformationMethod());
                }
                else {
                    password_edt.setTransformationMethod(new PasswordTransformationMethod());
                }

                password_edt.setSelection(password_edt.getText().length());
            }
        });
        
        
    }

    private void AnhXa() {
        register_txt = (TextView)findViewById(R.id.register);
        password_edt = (EditText) findViewById(R.id.password);
    }
}