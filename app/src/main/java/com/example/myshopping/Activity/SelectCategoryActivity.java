package com.example.myshopping.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myshopping.Adapter.CategoryAdapter;
import com.example.myshopping.Adapter.SelectCategoryAdapter;
import com.example.myshopping.Model.Category;
import com.example.myshopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectCategoryActivity extends AppCompatActivity {

    RecyclerView categoryRecycler;
    SelectCategoryAdapter categoryAdapter;
    FirebaseDatabase database= FirebaseDatabase.getInstance();;
    DatabaseReference myRef= database.getReference("Category");;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        AnhXa();
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

    private void AnhXa() {
        categoryRecycler = findViewById(R.id.recyclerview);

    }

    private void setCategoryRecycler(List<Category> categoryList) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        categoryRecycler.setLayoutManager(layoutManager);
        categoryAdapter = new SelectCategoryAdapter(this, categoryList);

        categoryRecycler.setAdapter( categoryAdapter);
    }


}