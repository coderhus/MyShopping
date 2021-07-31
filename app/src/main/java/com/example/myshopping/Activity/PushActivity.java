package com.example.myshopping.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshopping.R;
import com.example.myshopping.SupportCode.SupportCode;

public class PushActivity extends AppCompatActivity {

    TextView imageView_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        AnhXa();
        function();
    }

    private void function() {
        imageView_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PushActivity.this, SelectCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        imageView_category =findViewById(R.id.imageView_category);
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