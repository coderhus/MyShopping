package com.example.myshopping.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myshopping.R;
import com.example.myshopping.Adapter.CategoryAdapter;
import com.example.myshopping.Adapter.CategoryProductAdapter;
import com.example.myshopping.Adapter.PopularProductAdapter;
import com.example.myshopping.Model.Category;
import com.example.myshopping.Model.Products;
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

public class HomeActivity extends AppCompatActivity {


    RecyclerView popularRecycler, otherRecycler,categoryRecyclerView;
    PopularProductAdapter popularProductAdapter;
    CategoryProductAdapter categoryProductAdapter;
    CategoryAdapter categoryAdapter;
    TextView allCategory;
    BottomNavigationView bottomNavigationView;

    //
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Category");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home) ;

        //
        if(user == null ) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        AnhXa();
        bottomNavigationView();
        initList();
        function();
    }

    private void initList() {
        //
        List<Products> popularProductList = new ArrayList<>();

        popularProductList.add(new Products("",  ""," ","Float Cake Vietnam",7.05,R.drawable.popularfood1));
        popularProductList.add(new Products("",  ""," ","Float Cake Vietnam",7.05,R.drawable.popularfood1));
        popularProductList.add(new Products("",  ""," ","Float Cake Vietnam",7.05,R.drawable.popularfood1));
        popularProductList.add(new Products("",  ""," ","Float Cake Vietnam",7.05,R.drawable.popularfood1));
        popularProductList.add(new Products("",  ""," ","Float Cake Vietnam",7.05,R.drawable.popularfood1));
        popularProductList.add(new Products("",  ""," ","Float Cake Vietnam",7.05,R.drawable.popularfood1));

        setPopularRecycler(popularProductList);

        //

        List<Products> categoryProductList = new ArrayList<>();

        categoryProductList.add(new Products("4.2", "Friends Restaurant","Straberry Cake","Float Cake Vietnam",7.05,R.drawable.popularfood1));
        categoryProductList.add(new Products("4.2", "Friends Restaurant","Straberry Cake","Float Cake Vietnam",7.05,R.drawable.popularfood1));
        categoryProductList.add(new Products("4.2", "Friends Restaurant","Straberry Cake","Float Cake Vietnam",7.05,R.drawable.popularfood1));
        categoryProductList.add(new Products("4.2", "Friends Restaurant","Straberry Cake","Float Cake Vietnam",7.05,R.drawable.popularfood1));
        categoryProductList.add(new Products("4.2", "Friends Restaurant","Straberry Cake","Float Cake Vietnam",7.05,R.drawable.popularfood1));
        categoryProductList.add(new Products("4.2", "Friends Restaurant","Straberry Cake","Float Cake Vietnam",7.05,R.drawable.popularfood1));

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
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
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
        allCategory = (TextView) findViewById(R.id.allCategoryImage);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
    }

    //
    /* private void addCategory(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Category");
        String anh = "https://firebasestorage.googleapis.com/v0/b/myshopping-ee0cb.appspot.com/o/Category_photo%2FCategory_item%2FGi%C3%A0y%20d%C3%A9p%20nam.png?alt=media&token=c8acd6b7-4bff-4f7b-be05-0ffb91cbef01";
        Category add = new Category( "Đồ chơi","",0,anh);
        myRef.child(add.getId()).setValue(add);
    }*/

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

    }
}