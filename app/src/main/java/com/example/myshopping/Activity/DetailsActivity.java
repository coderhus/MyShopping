package com.example.myshopping.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myshopping.Model.Cart_item;
import com.example.myshopping.Model.Products;
import com.example.myshopping.Other.ManagementCart;
import com.example.myshopping.R;

public class DetailsActivity extends AppCompatActivity {


    private TextView itemNameTxt, feeTxt, descriptionTxt, numberOrderTxt;
    private ImageView plusBtn, minusBtn, picItem, addToCardBtn;
    private Cart_item object;
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

        plusBtn = (ImageView) findViewById(R.id.plus_image);
        minusBtn = (ImageView) findViewById(R.id.minus_image);
        picItem = (ImageView) findViewById(R.id.photoImage);
        addToCardBtn = (ImageView) findViewById(R.id.addToCart_Image);
    }

    private void getBundle() {

        // Lấy product bằng id trên firebase
        // String id_products = getIntent().getStringExtra("id_products");

        Products product = new Products("",  "Quán lung tung","Quần lót ",".........................",7.05,R.drawable.popularfood1);

        object = new Cart_item(product);

        picItem.setImageResource(object.getProducts().getPhotos());
        itemNameTxt.setText(object.getProducts().getName());
        feeTxt.setText("$" + object.getProducts().getPrice());
        descriptionTxt.setText(object.getProducts().getDescribe());
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder + 1;
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
                object.setNumberInCart(numberOrder);
                managementCart.insertItem(object);
            }
        });
    }


}