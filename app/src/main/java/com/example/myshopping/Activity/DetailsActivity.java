package com.example.myshopping.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myshopping.Model.Cart;
import com.example.myshopping.Model.Cart_item;
import com.example.myshopping.Model.Category;
import com.example.myshopping.Model.Products;
import com.example.myshopping.Other.ManagementCart;
import com.example.myshopping.R;
import com.example.myshopping.SupportCode.SupportCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class    DetailsActivity extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Cart").child(user.getUid());
    private TextView itemNameTxt, feeTxt, descriptionTxt, numberOrderTxt,restaurantName_txt;
    private ImageView plusBtn, minusBtn, picItem, addToCardBtn;
    private Products object;
    private int numberOrder = 1;
    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        managementCart = new ManagementCart(this);

        AnhXa();
        getBundle();
    }

    private void AnhXa() {
        itemNameTxt = (TextView) findViewById(R.id.itemName_txt);
        feeTxt = (TextView) findViewById(R.id.priceItem_txt);
        descriptionTxt = (TextView) findViewById(R.id.description);
        numberOrderTxt = (TextView) findViewById(R.id.numberOrderTxt);
        restaurantName_txt = findViewById(R.id.restaurantName_txt);
        plusBtn = (ImageView) findViewById(R.id.plus_image);
        minusBtn = (ImageView) findViewById(R.id.minus_image);
        picItem = (ImageView) findViewById(R.id.photoImage);
        addToCardBtn = (ImageView) findViewById(R.id.addToCart_Image);
    }

    private void getBundle() {

        object = (Products) getIntent().getSerializableExtra("object");
        Glide.with(this).load(object.getPhotos()).into(picItem);
        //picItem.setImageResource(object.getPhotos());
        itemNameTxt.setText(object.getName());
        feeTxt.setText("$" + object.getPrice());
        descriptionTxt.setText(object.getDescribe());
        numberOrderTxt.setText(String.valueOf(numberOrder));
        restaurantName_txt.setText(object.getName_seller());
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOrder+1<=object.getQuanity())
                numberOrder = numberOrder + 1;
                else
                    Toast.makeText(DetailsActivity.this, "Kho h??ng kh??ng ?????!", Toast.LENGTH_SHORT).show();
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        addToCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOrder<=object.getQuanity()) {
                    Products a = object;
                    a.setQuanity(numberOrder);
                    addCart(a);
                }
                else{
                    Toast.makeText(DetailsActivity.this, "Kho h??ng kh??ng ?????", Toast.LENGTH_SHORT).show();
                }
                // c??n ph???i tr??? quanity c???a product n???a nh??ng ch??a c?? database products
                //object.setNumberInCart(numberOrder);
//                managementCart.insertItem(object);

            }
        });
    }
    public void addCart(Products product ){
        // ?????u ti??n ?????c cart xem c?? nh???ng c??i j ????
        HashMap<String, Products> list_Products = new HashMap<String, Products>();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Cart get =dataSnapshot.getValue(Cart.class);
                if(get.getCount()==0){
                    list_Products.put(product.getId_products(),product);
                    get.setCount(1);
                    get.setId_product(list_Products);
                    myRef.setValue(get);
                }
                else{
                    String key = product.getId_products();
                    if(get.getList_Products().containsKey(key)){
                        int quanity = product.getQuanity();
                        get.getList_Products().get(key).addQuanity(quanity);
                    }
                    else{
                        get.addProduct(product);
                    }
                    get.setCount(get.getList_Products().size());
                    myRef.setValue(get);
                }

                Toast.makeText(DetailsActivity.this, "Added To Your Card", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(DetailsActivity.this,databaseError.toString(),Toast.LENGTH_LONG);
            }
        };
        myRef.addListenerForSingleValueEvent(postListener);
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