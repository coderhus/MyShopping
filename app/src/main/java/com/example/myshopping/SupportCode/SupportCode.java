package com.example.myshopping.SupportCode;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myshopping.Constants.Constants;
import com.example.myshopping.Model.Notifications;
import com.example.myshopping.Model.Rate;
import com.example.myshopping.Model.Users;
import com.example.myshopping.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SupportCode {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final int DEFAULT_MIN_WIDTH_QUALITY = 400;        // min pixels
    private static final String TAG = "ImagePicker";
    private static final String TEMP_IMAGE_NAME = "tempImage";
    public static int minWidthQuality = DEFAULT_MIN_WIDTH_QUALITY;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference("Users").child(SupportCode.getUID());
    //hiển thị giờ chat
    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }
    // lấy user id
    public static String getUID() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.getUid();
    }
    public static String currentData() {
        Calendar calendar = Calendar.getInstance();
        return sdf().format(calendar.getTimeInMillis());
    }

    public static SimpleDateFormat sdf() {
        return new SimpleDateFormat("yyyy-MM-dd hh-mm-ss a", Locale.US);
    }
    public static void updateOnlineStatus(String status) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(getUID());
        Map<String, Object> map = new HashMap<>();
        map.put("online", status);
        databaseReference.updateChildren(map);
    }
    public static void addNotification(String hisID,String description,int type) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notification").child(hisID);
        Notifications notification = new Notifications();
        notification.setId(getUID());
        notification.setDescription(description);
        notification.setTime(String.valueOf(System.currentTimeMillis()));
        notification.setType(type);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
               notification.setPhoto(user.getPhoto());
               databaseReference.child(notification.getTime()).setValue(notification);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static void addRate(String productID,double rate){
        // set rate ban daau
        if(rate==-1){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Rate");
            databaseReference.child(productID).setValue(new Rate());
        }
        else {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Rate").child(productID);
            databaseReference.child(getUID()).setValue(rate);
            changeRate(productID, rate);
        }
    }
    public static void changeRate(String productID,double rateadd){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Rate").child(productID);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                Rate rate = snapshot.getValue(Rate.class);
                rate.updateRate(rateadd);
                Map<String, Object> map = new HashMap<>();
                map.put("rate", rate.getRate());
                map.put("count", rate.getCount());
                databaseReference.updateChildren(map);

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }

}
