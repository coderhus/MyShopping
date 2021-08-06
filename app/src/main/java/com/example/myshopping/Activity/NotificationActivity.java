package com.example.myshopping.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myshopping.Adapter.NotifiAdapter;
import com.example.myshopping.Model.Category;
import com.example.myshopping.Model.Notifications;
import com.example.myshopping.Model.Users;
import com.example.myshopping.R;
import com.example.myshopping.SupportCode.SupportCode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;
    NotifiAdapter notifiAdapter;
    List<Notifications> list;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Notification").child(SupportCode.getUID()).child("list_notification");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nofitication);
        Anhxa();
        bottomNavigationView();
        // vao trang thong bao thi noti ve 0
        SupportCode.changecountNoti(SupportCode.getUID(),0);
        initList();
    }

    private void initList() {
        list =new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {
                    Notifications a = postsnap.getValue(Notifications.class);
                    list.add(a);
                }
                //
                if(!list.isEmpty()){
                    setNotifiRecycler(list);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        setNotifiRecycler(list);
    }

    private void setNotifiRecycler(List<Notifications> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        notifiAdapter = new NotifiAdapter(this, list);
        recyclerView.setAdapter(notifiAdapter);
    }

    private void Anhxa(){
        recyclerView = findViewById(R.id.recyclerview);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
    }
    private void bottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.nav_notifications);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(NotificationActivity.this, HomeActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.nav_cart:
                        startActivity(new Intent(NotificationActivity.this, CartActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.nav_notifications:
                        break;
                    case R.id.nav_chat:
                        startActivity(new Intent(NotificationActivity.this, ChatActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.nav_person:
                        startActivity(new Intent(NotificationActivity.this, PersonActivity.class));
                        overridePendingTransition(0, 0);
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