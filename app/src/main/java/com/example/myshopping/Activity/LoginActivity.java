package com.example.myshopping.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView register_txt;
    EditText password_edt,gmail_login;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        // lưu gmail với pass sau khi tạo ở register
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String gmail = bundle.getString("gmail", "");
            String pass =bundle.getString("pass", "");
            gmail_login.setText(gmail);
            password_edt.setText(pass);
        }
    }

    private void login(){
        String email = gmail_login.getText().toString();
        String password = password_edt.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            if(mAuth.getCurrentUser().isEmailVerified()) {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                            else{
                                mAuth.signOut();
                                Toast.makeText(LoginActivity.this,"Vui lòng xác nhận gmail trước khi đăng nhập",Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Sai gmail hoặc mật khẩu!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void AnhXa() {
        register_txt = (TextView)findViewById(R.id.register);
        password_edt = (EditText) findViewById(R.id.password);
        gmail_login = findViewById(R.id.gmail_login);
        login = findViewById(R.id.login);
    }
}