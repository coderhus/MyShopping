package com.example.myshopping.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.myshopping.Model.Cart_item;
import com.example.myshopping.Model.Category;
import com.example.myshopping.Model.Products;
import com.example.myshopping.Other.ChangeNumberItemsListener;
import com.example.myshopping.Other.ManagementCart;
import com.example.myshopping.R;
import com.example.myshopping.Adapter.CartListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Cart").child(user.getUid()).child("list_Products");

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private TextView totalItemPTxt, salePTxt, servicePTxt, totalPTxt, emptyTxt;
    private ScrollView scrollView;
    private ManagementCart managementCart;

    private double sale;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        managementCart = new ManagementCart(this);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {
                    if(postsnap!=null){
                    Products a = postsnap.getValue(Products.class);
                    managementCart.insertItem(a);
                }
                //
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        AnhXa();

        initList();
        calculateCard();
        bottomNavigationView();

    }


    private void initList() {

        setCartRecycler();
        if (managementCart.getListCard().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void bottomNavigationView() {
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

    private void calculateCard() {
        double per_sale = 0.0;
        double delivery = 10;

        sale = Math.round((managementCart.getTotalFee() * per_sale) * 100.0) / 100.0;
        double total = Math.round((managementCart.getTotalFee() - sale + delivery) * 100.0) / 100.0;
        double itemTotal = Math.round(managementCart.getTotalFee() * 100.0) / 100.0;

        totalItemPTxt.setText("$" + itemTotal);
        salePTxt.setText("$" + sale);
        servicePTxt.setText("$" + delivery);
        totalPTxt.setText("$" + total);
    }

    private void AnhXa() {
        emptyTxt = (TextView) findViewById(R.id.emptyTxt);
        salePTxt = (TextView) findViewById(R.id.txtSalePrice);
        servicePTxt = (TextView) findViewById(R.id.txtServicePrice);
        totalItemPTxt = (TextView) findViewById(R.id.txtTotalItemPrice);
        totalPTxt = (TextView) findViewById(R.id.txtTotalPrice);

        scrollView = findViewById(R.id.scrollview_cart);
        recyclerViewList = findViewById(R.id.recyclerview);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
    }

    private void setCartRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCard(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCard();
            }
        });
        recyclerViewList.setAdapter(adapter);
    }
}