package com.example.myshopping.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myshopping.Adapter.DialogAdapter;
import com.example.myshopping.Model.Chat;
import com.example.myshopping.R;
import com.example.myshopping.SupportCode.SupportCode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    RecyclerView dialog;
    DialogAdapter adapter;
    BottomNavigationView bottomNavigationView;
    SupportCode sp = new SupportCode();
    private String hisID, hisImage, myID, chatID = null, myImage, myName, audioPath;
    private TextView tangduy,minhdung;
    private EditText title,message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Anhxa();
        bottomNavigationView();
        List<Chat> chatList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("ChatList").child(sp.getUID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot postsnap: snapshot.getChildren()) {
                    Chat a = postsnap.getValue(Chat.class);
                    chatList.add(a);
                }
                //
                if(!chatList.isEmpty()){
                    setchatRecycle(chatList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void setchatRecycle(List<Chat> chatList) {

        dialog = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        dialog.setLayoutManager(layoutManager);
        adapter = new DialogAdapter(this, chatList);
        dialog.setAdapter(adapter);

    }
    private void Anhxa(){

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
    }
    private void bottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.nav_chat);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(ChatActivity.this,HomeActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_cart:
                        startActivity(new Intent(ChatActivity.this, CartActivity.class));

                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_notifications:
                        startActivity(new Intent(ChatActivity.this, NotificationActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_chat:
                        break;
                    case R.id.nav_person:
                        startActivity(new Intent(ChatActivity.this,PersonActivity.class));

                        overridePendingTransition(0,0);
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