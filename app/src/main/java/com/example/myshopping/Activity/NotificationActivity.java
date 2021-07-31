package com.example.myshopping.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myshopping.R;
import com.example.myshopping.SupportCode.SupportCode;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nofitication);
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