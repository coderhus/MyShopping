package com.example.myshopping.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myshopping.Constants.Constants;
import com.example.myshopping.Model.Orders;
import com.example.myshopping.Model.Orders_item;
import com.example.myshopping.Model.Products;
import com.example.myshopping.Other.ChangeNumberItemsListener;
import com.example.myshopping.Other.ManagementCart;
import com.example.myshopping.R;
import com.example.myshopping.Adapter.CartListAdapter;
import com.example.myshopping.SupportCode.SupportCode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Cart").child(user.getUid()).child("list_Products");
    static boolean check = true;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private TextView totalItemPTxt, salePTxt, servicePTxt, totalPTxt, emptyTxt,orderConfirm;
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
        orderConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeOrder();
            }
        });
    }
    private void makeOrder(){
        List<Products> list =managementCart.getListCard();
        HashSet<String> temp = new HashSet<String>();
        for(int i=0;i<list.size();i++){
           temp.add(list.get(i).getId_seller());
            if(!checkQuanity(list.get(i))){
                Toast.makeText(CartActivity.this,list.get(i).getName()+" không đủ hàng",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        List<String> countSeller = new ArrayList<String>();
        countSeller.addAll(temp);
        String time = String.valueOf(System.currentTimeMillis());
        for(int i=0;i<countSeller.size();i++){
            String id = countSeller.get(i);
            Orders orders = new Orders(time,id,SupportCode.getUID(),0,time);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
            databaseReference.child(time).setValue(orders);
            for(int j=0;j<list.size();j++){
                if(list.get(j).getId_seller().equals(id)){
                    databaseReference.child(time).child("list_order").child(list.get(j).getId_products()).setValue(list.get(j));
                }
            }
            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Orders_Buyer");
            // status 0 là chưa xác nhận hàng
            databaseReference1.child(SupportCode.getUID()).child(time).setValue(0);
            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Orders_Seller");
            databaseReference2.child(id).child(time).setValue(0);
            getToken("Có người đặt hàng",id,time);
        }
        managementCart.clear();

        Toast.makeText(CartActivity.this,"Quý khách đã đặt hàng thành công!",Toast.LENGTH_SHORT).show();
        Intent a = new Intent(CartActivity.this,HomeActivity.class);
        startActivity(a);
    }
    private boolean checkQuanity(Products products){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Products").child(products.getId_products());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                Products temp = snapshot.getValue(Products.class);
                if(temp.getQuanity()<products.getQuanity()){
                    check = false;
                }
                else{
                    temp.addQuanity(-products.getQuanity());
                    databaseReference.setValue(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
        return check;
    }
    private void getToken(String message, String hisID,String chatID){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(hisID);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                String token = snapshot.child("token").getValue().toString();
                JSONObject to = new JSONObject();
                JSONObject data =new JSONObject();
                FirebaseDatabase.getInstance().getReference("Users").child(SupportCode.getUID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue().toString();
                        //chua co
                        String photo ="https://anhdep123.com/wp-content/uploads/2020/05/cho-con.jpg" ;
                        try{
                            data.put("title",name );
                            data.put("message", message);
                            data.put("hisID", SupportCode.getUID());
                            data.put("hisImage",photo);
                            data.put("chatID", chatID);

                            to.put("to", token);
                            to.put("data", data);
                            sendNotification(to);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendNotification(JSONObject to) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.NOTIFICATION_URL, to, response -> {
            Log.d("notification", "sendNotification: " + response);
        }, error -> {
            Log.d("notification", "sendNotification: " + error);
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "key=" + Constants.SERVER_KEY);
                map.put("Content-Type", "application/json");
                return map;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
    private void bottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.nav_cart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(CartActivity.this,HomeActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_cart:
                        break;
                    case R.id.nav_notifications:
                        startActivity(new Intent(CartActivity.this, NotificationActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_chat:
                        startActivity(new Intent(CartActivity.this,ChatActivity.class));

                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_person:
                        startActivity(new Intent(CartActivity.this,PersonActivity.class));

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
        orderConfirm = findViewById(R.id.orderConfirm);
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