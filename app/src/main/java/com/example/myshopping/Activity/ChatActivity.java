package com.example.myshopping.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myshopping.Constants.Constants;
import com.example.myshopping.Nofication.NoficationService;
import com.example.myshopping.R;
import com.example.myshopping.SupportCode.SupportCode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    SupportCode sp = new SupportCode();
    private String hisID, hisImage, myID, chatID = null, myImage, myName, audioPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getToken("alo","LUcIR7GIqjXdzZlQ3FQXsh4qWXy2","https://firebasestorage.googleapis.com/v0/b/myshopping-ee0cb.appspot.com/o/Category_photo%2FCategory_item%2FGi%C3%A0y%20d%C3%A9p%20n%E1%BB%AF.png?alt=media&token=7d8e73e8-70bd-431e-8e4d-06bf1402a50a","1");
    }
    private void getToken(String message, String hisID,String hisImage,String chatID){
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
                    data.put("title", "duy");
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