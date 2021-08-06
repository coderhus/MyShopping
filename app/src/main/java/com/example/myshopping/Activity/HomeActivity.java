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
import android.widget.Toast;

import com.example.myshopping.Model.Cart;
import com.example.myshopping.Notification.NotificationService;
import com.example.myshopping.R;
import com.example.myshopping.Adapter.CategoryAdapter;
import com.example.myshopping.Adapter.CategoryProductAdapter;
import com.example.myshopping.Adapter.PopularProductAdapter;
import com.example.myshopping.Model.Category;
import com.example.myshopping.Model.Products;
import com.example.myshopping.SupportCode.SupportCode;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    RecyclerView popularRecycler, otherRecycler,categoryRecyclerView, otherRecycler_2;
    PopularProductAdapter popularProductAdapter;
    CategoryProductAdapter categoryProductAdapter;
    CategoryAdapter categoryAdapter;
    TextView allCategory;
    ScrollView scrollView;
    BottomNavigationView bottomNavigationView;

    //
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Category");
    DatabaseReference myRef1 = database.getReference("Products");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home) ;

        //
        updateToken();
        AnhXa();
        bottomNavigationView();
        initList();
        function();
    }

    //update token
    private void updateToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(HomeActivity.this, "may ao k chay dc token", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        NotificationService a = new NotificationService();
                        a.onNewToken(token);
                        // Toast.makeText(HomeActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initList() {
        //
        List<Products> popularProductList = new ArrayList<>();
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                popularProductList.clear();
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {
                    Products a = postsnap.getValue(Products.class);
                    popularProductList.add(a);
                }
                //
                if(!popularProductList.isEmpty()){
                    setPopularRecycler(popularProductList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        popularProductList.add(new Products("4_1",  "Friends Restaurant","Straberry Cake ","Float Cake Vietnam",7.05,10,""));
//
        setPopularRecycler(popularProductList);

        //

        List<Products> categoryProductList = new ArrayList<>();
        // categoryProductList.add(new Products("4_2", "Friends Restaurant","Straberry Cake","Float Cake Vietnam",7.05,10,""));
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoryProductList.clear();
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {
                    Products a = postsnap.getValue(Products.class);
                    categoryProductList.add(a);
                }
                //
                if(!categoryProductList.isEmpty()){
                    setAsiaRecycler(categoryProductList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        setAsiaRecycler(categoryProductList);

        //
        List<Category> categoryList = new ArrayList<>();
        setCategoryRecycler(categoryList);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoryList.clear();
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {
                    Category a = postsnap.getValue(Category.class);
                    categoryList.add(a);
                }
                //
                if(!categoryList.isEmpty()){
                    setCategoryRecycler(categoryList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void function(){
        //
        scrollView.setSmoothScrollingEnabled(false);
        //  allCategory
        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AllCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void bottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        break;
                    case R.id.nav_cart:
                        startActivity(new Intent(HomeActivity.this,CartActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_notifications:
                        startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_chat:
                        startActivity(new Intent(HomeActivity.this,ChatActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_person:
                        startActivity(new Intent(HomeActivity.this,PersonActivity.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });
        //thêm số lượng hàng ở cart, có thể thêm thông báo về sau
        DatabaseReference myRef1 = database.getReference("Cart").child(SupportCode.getUID());
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                Cart a = snapshot.getValue(Cart.class);
                bottomNavigationView.getOrCreateBadge(R.id.nav_cart).setNumber(a.getCount());
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
        // thêm số lượng thông báo
        DatabaseReference myRef2 = database.getReference("Notification").child(SupportCode.getUID());
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                int count = snapshot.child("count").getValue(Integer.class);
                bottomNavigationView.getOrCreateBadge(R.id.nav_notifications).setNumber(count);
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }


    private void AnhXa() {
        scrollView =findViewById(R.id.scrollView);
        otherRecycler_2 = (RecyclerView) findViewById(R.id.other_recycler_2);
        allCategory = (TextView) findViewById(R.id.allCategoryImage);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
    }

    // thêm category lên firebase dùng khi muốn up thêm 1 category nữa

//     private void addCategory(){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Category");
//        String anh = "https://firebasestorage.googleapis.com/v0/b/myshopping-ee0cb.appspot.com/o/Category_photo%2FCategory_item%2F%C4%90%E1%BB%93%20%C4%83n.png?alt=media&token=03726142-fe7e-4287-b8bb-9325bf7fe7f5";
//        Category add = new Category( "Đồ ăn","",0,anh);
//        myRef.child(add.getId()).setValue(add);
//    }

    private void setCategoryRecycler(List<Category> categoryList) {
        categoryRecyclerView = findViewById(R.id.categoryRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this,categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setPopularRecycler(List<Products> popularProductList) {

        popularRecycler = findViewById(R.id.popular_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        popularRecycler.setLayoutManager(layoutManager);
        popularProductAdapter = new PopularProductAdapter(this, popularProductList);
        popularRecycler.setAdapter(popularProductAdapter);

    }

    private void setAsiaRecycler(List<Products> categoryProductList) {

        otherRecycler = findViewById(R.id.other_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        otherRecycler.setLayoutManager(layoutManager);
        categoryProductAdapter = new  CategoryProductAdapter(this, categoryProductList);
        otherRecycler.setAdapter( categoryProductAdapter);


        RecyclerView.LayoutManager layoutManager_2 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        otherRecycler_2.setLayoutManager(layoutManager_2);
        otherRecycler_2.setAdapter( categoryProductAdapter);
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