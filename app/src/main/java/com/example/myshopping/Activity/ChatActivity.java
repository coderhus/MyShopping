package com.example.myshopping.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myshopping.Adapter.CategoryProductAdapter;
import com.example.myshopping.Adapter.DialogAdapter;
import com.example.myshopping.Constants.Constants;
import com.example.myshopping.Model.Category;
import com.example.myshopping.Model.Chat;
import com.example.myshopping.Model.Products;
import com.example.myshopping.Model.Users;
import com.example.myshopping.Nofication.NoficationService;
import com.example.myshopping.R;
import com.example.myshopping.SupportCode.SupportCode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        tangduy = findViewById(R.id.guichoTangDuy);
//        minhdung = findViewById(R.id.guichoMinhDung);
//        title = findViewById(R.id.textlagi);
//        message = findViewById(R.id.messagelagi);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
    }
    private void getToken(String title,String message, String hisID,String hisImage,String chatID){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(hisID);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                String token = snapshot.child("token").getValue().toString();
                JSONObject to = new JSONObject();
                JSONObject data =new JSONObject();
                try{
//                    data.put("title", myName);
//                    data.put("message", message);
//                    data.put("hisID", myID);
//                    data.put("hisImage", myImage);
//                    data.put("chatID", chatID);
                    data.put("title",title );
                    data.put("message", message);
                    data.put("hisID", sp.getUID());
                    data.put("hisImage", hisImage);
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
}