package com.example.myshopping.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myshopping.Adapter.ChatAdapter;
import com.example.myshopping.Constants.Constants;
import com.example.myshopping.Model.Chat;
import com.example.myshopping.Model.Messages;
import com.example.myshopping.Model.Users;
import com.example.myshopping.R;
import com.example.myshopping.SupportCode.SupportCode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Permissions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {
    RecyclerView chat_view;
    ChatAdapter adapter;
    private String hisID, hisImage,hisName, myID, chatID = null, myImage, myName, audioPath;
    private SupportCode sp = new SupportCode();
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private Permissions permissions;
    TextView hisNametext;
    EditText msgText;
    ImageView btnDataSend,msgBack,msgInfo,specificuserimageinimageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Anhxa();
        myID = sp.getUID();
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        chatID = getIntent().getStringExtra("chatID");
        hisImage = getIntent().getStringExtra("hisImage");
        hisName = getIntent().getStringExtra("name");
        hisID = getIntent().getStringExtra("hisID");
        Glide.with(MessageActivity.this).load(hisImage).into(specificuserimageinimageview);
        hisNametext.setText(hisName);
        // xét onclick
        //nút gửi
        btnDataSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = msgText.getText().toString();
                if(msg.equals("")){
                    Toast.makeText(MessageActivity.this,"Tin nhắn không được để trống",Toast.LENGTH_SHORT);
                }
                else{
                    sendMessage(msg);
                    getToken(msg,hisID,chatID);
                }

                msgText.setText("");
            }
        });
        // nút back
        msgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // nút info shop
//        msgInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // vào trang chủ của shop đó
//            }
//        });
        // doc mess
        if(chatID!=null){
            readMessages(chatID);
        }
    }
    public void Anhxa(){
        hisNametext = findViewById(R.id.Nameofspecificuser);
        msgText = findViewById(R.id.getmessage);
        btnDataSend =findViewById(R.id.imageviewsendmessage);
        msgBack = findViewById(R.id.backbuttonofspecificchat);
//        msgInfo =findViewById(R.id.msgInfo);
        specificuserimageinimageview=findViewById(R.id.specificuserimageinimageview);
    }
    private void createChat(String msg) {
        Long tsLong = System.currentTimeMillis()/1000;
        String time = tsLong.toString();
        Chat a = new Chat(time,hisID,hisName,msg,hisImage,time);
        databaseReference = FirebaseDatabase.getInstance().getReference("ChatList").child(myID);
        databaseReference.child(time).setValue(a);
        //
        FirebaseDatabase.getInstance().getReference("Users").child(myID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                Users me = snapshot.getValue(Users.class);
                Chat b = new Chat(time,myID,me.getName(),msg,me.getPhoto(),time);
                databaseReference = FirebaseDatabase.getInstance().getReference("ChatList").child(hisID);
                databaseReference.child(time).setValue(b);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //
        databaseReference = FirebaseDatabase.getInstance().getReference("Chat").child(time);
        Messages messageModel = new Messages(time,myID,hisID,msg,time,"text");
        databaseReference.push().setValue(messageModel);
        getToken(msg,hisID,time);
    }

    private void sendMessage(String msg) {
        if (chatID == null) {
            createChat(msg);

        } else {
            Long tsLong = System.currentTimeMillis()/1000;
            String date = tsLong.toString();
            Messages messageModel = new Messages(chatID,myID, hisID, msg, date, "text");
            databaseReference = FirebaseDatabase.getInstance().getReference("Chat").child(chatID);
            databaseReference.push().setValue(messageModel);

            Map<String, Object> map = new HashMap<>();
            map.put("lastMessage", msg);
            map.put("date", date);
            databaseReference = FirebaseDatabase.getInstance().getReference("ChatList").child(myID).child(chatID);
            databaseReference.updateChildren(map);

            databaseReference = FirebaseDatabase.getInstance().getReference("ChatList").child(hisID).child(chatID);
            Map<String, Object> update = new HashMap<>();
            update.put("lastMessage", msg);
            update.put("date", date);
            databaseReference.updateChildren(map);

        }
    }
    private void readMessages(String chatID) {
        List<Messages> messList = new ArrayList<>();
        Query query = FirebaseDatabase
                .getInstance().getReference().child("Chat")
                .child(chatID);
        query.keepSynced(true);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                messList.clear();
                for (DataSnapshot postsnap: snapshot.getChildren()) {
                    Messages a = postsnap.getValue(Messages.class);
                    messList.add(a);
                }
                //
                if(!messList.isEmpty()){
                    setchatRecycle(messList);
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
    private void setchatRecycle(List<Messages> MessageList) {
        chat_view = findViewById(R.id.recyclerviewofspecific);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        chat_view.setLayoutManager(layoutManager);
        chat_view.scrollToPosition(MessageList.size() - 1);
        adapter = new ChatAdapter(this, MessageList);
        chat_view.setAdapter(adapter);
    }
    // gửi noti
    private void getToken(String message, String hisID,String chatID){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(hisID);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                String token = snapshot.child("token").getValue().toString();
                JSONObject to = new JSONObject();
                JSONObject data =new JSONObject();
               FirebaseDatabase.getInstance().getReference("Users").child(sp.getUID()).addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull  DataSnapshot snapshot) {
                       String name = snapshot.child("name").getValue().toString();
                      //chua co
                       String photo ="https://anhdep123.com/wp-content/uploads/2020/05/cho-con.jpg" ;
                       String token = snapshot.child("token").getValue().toString();
                       try{
                           data.put("title",name );
                           data.put("message", message);
                           data.put("hisID", sp.getUID());
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
}