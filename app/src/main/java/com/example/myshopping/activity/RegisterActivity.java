package com.example.myshopping.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.EditText;

import com.example.myshopping.R;

public class RegisterActivity extends AppCompatActivity {

    EditText inputPW, inputCPW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AnhXa();

        inputPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputPW.getTransformationMethod().getClass().getSimpleName() .equals("PasswordTransformationMethod")) {
                    inputPW.setTransformationMethod(new SingleLineTransformationMethod());
                }
                else {
                    inputPW.setTransformationMethod(new PasswordTransformationMethod());
                }

                inputPW.setSelection(inputPW.getText().length());
            }
        });
    }

    private void AnhXa() {
        inputPW =(EditText) findViewById(R.id.inputPassword);
        inputCPW =(EditText) findViewById(R.id.inputConformPassword);
    }
}