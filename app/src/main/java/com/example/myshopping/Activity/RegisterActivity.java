package com.example.myshopping.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myshopping.Model.Cart;
import com.example.myshopping.Model.Products;
import com.example.myshopping.Model.Rate;
import com.example.myshopping.R;
import com.example.myshopping.Model.Users;
import com.example.myshopping.SupportCode.SupportCode;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    EditText username,gmail,inputPW, inputCPW;
    Button register;
    //TextView alreadyHaveAccount;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        AnhXa();

        //
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

        //
        inputCPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputCPW.getTransformationMethod().getClass().getSimpleName() .equals("PasswordTransformationMethod")) {
                    inputCPW.setTransformationMethod(new SingleLineTransformationMethod());
                }
                else {
                    inputCPW.setTransformationMethod(new PasswordTransformationMethod());
                }

                inputCPW.setSelection(inputCPW.getText().length());
            }
        });

        //
        /*  alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailExistsOrNot();
            }
        });
    }

    void checkEmailExistsOrNot(){
        int n =0;
        mAuth.fetchSignInMethodsForEmail(gmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.getResult().getSignInMethods().size() == 0){
                    // email not existed
                    CreateAccount();
                }else {
                    // email existed
                    Toast.makeText(RegisterActivity.this, "T??i kho???n gmail ???? t???n t???i", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void CreateAccount(){
        final String mail = gmail.getText().toString();
        String password = inputPW.getText().toString();
        String password2 = inputCPW.getText().toString();
        if(!password.equals(password2)){
            Toast.makeText(RegisterActivity.this,"M???t kh???u kh??ng kh???p!",Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final FirebaseUser userFB = task.getResult().getUser();
                                if(userFB!=null){
                                    userFB.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Toast.makeText(RegisterActivity.this, "T???o t??i kho???n th??nh c??ng",
                                                    Toast.LENGTH_SHORT).show();
                                            pushInfotoDatabase(mail);
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("gmail",mail); // Truy???n m???t String
                                            bundle.putString("pass", password);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }
                                    });
                                }

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterActivity.this, "T???o t??i kho???n th???t b???i. Qu?? kh??ch vui l??ng th??? l???i",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    public void createNoti(String id){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Rate");
        Map<String, Object> map = new HashMap<>();
        map.put("count", 0);
        databaseReference.child(id).setValue(map);
    }
    public void pushInfotoDatabase(String mail){
        //t???o database c???a users
        String name = username.getText().toString();
        String id = mAuth.getUid();
        final String timecreate =  String.valueOf(System.currentTimeMillis());
        Users user = new Users(id,name,timecreate,mail);
        myRef.child("Users").child(id).setValue(user);
        // t???o database c???a cart ->id users(X??t m???c ?????nh l?? 0)

        Cart a = new Cart(id,0,null);
        myRef.child("Cart").child(id).setValue(a);
        createNoti(id);
    }

    private void AnhXa() {
        inputPW =(EditText) findViewById(R.id.inputPassword);
        inputCPW =(EditText) findViewById(R.id.inputConformPassword);
        username = findViewById(R.id.inputUsername);
        gmail = findViewById(R.id.inputEmail);
        register = findViewById(R.id.btnRegister);
        // alreadyHaveAccount=findViewById(R.id.alreadyHaveAccount);
    }
    @Override
    protected void onResume() {
        SupportCode.updateOnlineStatus("online");
        super.onResume();
    }

    @Override
    protected void onPause() {
        SupportCode.updateOnlineStatus(String.valueOf(System.currentTimeMillis()));
        super.onPause();
    }
}