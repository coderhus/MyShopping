package com.example.myshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.example.myshopping.activity.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new CountDownTimer(500,250) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
//                if(user == null) {
//                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                }
//                else{
//                    Intent intent = new Intent(MainActivity.this, Postlish_main_testActivity.class);
//                    startActivity(intent);
//                }
                  Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                  startActivity(intent);
            }
        }.start();
    }
}