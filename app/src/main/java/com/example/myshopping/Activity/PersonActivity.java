package com.example.myshopping.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import com.example.myshopping.Adapter.PagerPersonAdapter;
import com.example.myshopping.R;
import com.example.myshopping.SupportCode.SupportCode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class PersonActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TabLayout tabLayout;
    TabItem tab1,tab2;
    ViewPager viewPager;
    PagerPersonAdapter personAdapter;
    TextView sell_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        AnhXa();
        bottomNavigationView();
        function();

    }

    private void function() {
        //
        viewPager.setAdapter(personAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //
        sell_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonActivity.this, PushActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        //
        tabLayout=(TabLayout) findViewById(R.id.tablayout_person);
        tab1=(TabItem) findViewById(R.id.mua_hang);
        tab2=(TabItem) findViewById(R.id.ban_hang);
        viewPager =(ViewPager) findViewById(R.id.viewPage);
        bottomNavigationView =(BottomNavigationView) findViewById(R.id.bottom_navigation);
        sell_txt=(TextView) findViewById(R.id.sell_txt);
        //
        personAdapter = new PagerPersonAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
    }

    private void bottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.nav_person);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_cart:
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_notifications:
                        startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_chat:
                        startActivity(new Intent(getApplicationContext(),ChatActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_person:
                        break;
                }
                return false;
            }
        });
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