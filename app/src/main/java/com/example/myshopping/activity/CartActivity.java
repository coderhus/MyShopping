package com.example.myshopping.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myshopping.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        AnhXa();

        // BottomNavigationView
        clickBottomNavigationView();
    }

    private void clickBottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.nav_cart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_cart:
                        break;
                    case R.id.nav_notifications:
                        startActivity(new Intent(getApplicationContext(),NofiticationActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_chat:
                        startActivity(new Intent(getApplicationContext(),ChatActivity.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });
    }

    private void AnhXa() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
    }
}