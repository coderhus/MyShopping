package com.example.myshopping.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myshopping.R;
import com.example.myshopping.adapter.CategoryAdapter;
import com.example.myshopping.adapter.CategoryProductAdapter;
import com.example.myshopping.adapter.PopularProductAdapter;
import com.example.myshopping.model.Category;
import com.example.myshopping.model.Products;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    RecyclerView popularRecycler, otherRecycler,categoryRecyclerView;
    PopularProductAdapter popularProductAdapter;
    CategoryProductAdapter categoryProductAdapter;
    CategoryAdapter categoryAdapter;
    TextView allCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home) ;

        AnhXa();
        if(user == null) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        // allCategory
        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, AllCategoryActivity.class);
                startActivity(i);
            }
        });

        //
        List<Products> popularProductList = new ArrayList<>();

        popularProductList.add(new Products("",  ""," ","Float Cake Vietnam","$7.05",R.drawable.popularfood1));
        popularProductList.add(new Products("",  ""," ","Float Cake Vietnam","$7.05",R.drawable.popularfood1));
        popularProductList.add(new Products("",  ""," ","Float Cake Vietnam","$7.05",R.drawable.popularfood1));
        popularProductList.add(new Products("",  ""," ","Float Cake Vietnam","$7.05",R.drawable.popularfood1));
        popularProductList.add(new Products("",  ""," ","Float Cake Vietnam","$7.05",R.drawable.popularfood1));
        popularProductList.add(new Products("",  ""," ","Float Cake Vietnam","$7.05",R.drawable.popularfood1));

        setPopularRecycler(popularProductList);

        //

        List<Products> categoryProductList = new ArrayList<>();

        categoryProductList.add(new Products("4.2", "Friends Restaurant","Straberry Cake","Float Cake Vietnam","$7.05",R.drawable.popularfood1));
        categoryProductList.add(new Products("4.2", "Friends Restaurant","Straberry Cake","Float Cake Vietnam","$7.05",R.drawable.popularfood1));
        categoryProductList.add(new Products("4.2", "Friends Restaurant","Straberry Cake","Float Cake Vietnam","$7.05",R.drawable.popularfood1));
        categoryProductList.add(new Products("4.2", "Friends Restaurant","Straberry Cake","Float Cake Vietnam","$7.05",R.drawable.popularfood1));
        categoryProductList.add(new Products("4.2", "Friends Restaurant","Straberry Cake","Float Cake Vietnam","$7.05",R.drawable.popularfood1));
        categoryProductList.add(new Products("4.2", "Friends Restaurant","Straberry Cake","Float Cake Vietnam","$7.05",R.drawable.popularfood1));

        setAsiaRecycler(categoryProductList);

        //
        List<Category> categoryList = new ArrayList<>();

        categoryList.add(new Category( ""," ",0,R.drawable.ic_home_fish));
        categoryList.add(new Category( ""," ",0,R.drawable.ic_home_fish));
        categoryList.add(new Category( ""," ",0,R.drawable.ic_home_fish));
        categoryList.add(new Category( ""," ",0,R.drawable.ic_home_fish));
        categoryList.add(new Category( ""," ",0,R.drawable.ic_home_fish));
        categoryList.add(new Category( ""," ",0,R.drawable.ic_home_fish));
        categoryList.add(new Category( ""," ",0,R.drawable.ic_home_fish));
        categoryList.add(new Category( ""," ",0,R.drawable.ic_home_fish));

        setCategoryRecycler(categoryList);
    }

    private void AnhXa() {
        allCategory = (TextView) findViewById(R.id.allCategoryImage);

    }

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