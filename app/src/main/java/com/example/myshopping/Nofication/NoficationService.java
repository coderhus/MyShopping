package com.example.myshopping.Nofication;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.example.myshopping.Activity.ChatActivity;
import com.example.myshopping.Activity.DetailsActivity;
import com.example.myshopping.Activity.MessageActivity;
import com.example.myshopping.Activity.NotificationActivity;
import com.example.myshopping.Constants.Constants;
import com.example.myshopping.R;
import com.example.myshopping.SupportCode.SupportCode;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class NoficationService extends FirebaseMessagingService {
    private SupportCode a = new SupportCode();

    @Override
    public void onMessageReceived(@NonNull  RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> map = remoteMessage.getData();
            String title = map.get("title");
            String message = map.get("message");
            String hisID = map.get("hisID");
            String hisImage = map.get("hisImage");
            String chatID = map.get("chatID");

            Log.d("TAG", "onMessageReceived: chatID is " + chatID + "\n hisID" + hisID);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
                createOreoNotification(title, message, hisID, hisImage, chatID);
            else {
                try {
                    createNormalNotification(title, message, hisID, hisImage, chatID);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else Log.d("TAG", "onMessageReceived: no data ");
        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(String s) {
        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener(new OnCompleteListener<InstallationTokenResult>() {
            @Override
            public void onComplete(@NonNull  Task<InstallationTokenResult> task) {

            }
        });
        updateToken(s);
        super.onNewToken(s);
    }

    private void updateToken(String token){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(a.getUID());
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        databaseReference.updateChildren(map);
    }
    private void createNormalNotification(String title, String message, String hisID, String hisImage, String chatID) throws ExecutionException, InterruptedException {
        Bitmap theBitmap = null;
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        try {
            theBitmap = Glide.
                    with(this).
                    load(hisImage).
                    asBitmap().
                    into(-1,-1).
                    get();
        } catch (final ExecutionException e) {

        } catch (final InterruptedException e) {

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Constants.CHANNEL_ID);
        builder.setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(theBitmap)
                .setAutoCancel(true)
                .setShowWhen(true)
                .setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null))
                .setSound(uri);

        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra("chatID", chatID);
        intent.putExtra("hisID", hisID);
        intent.putExtra("name", title);
        intent.putExtra("hisImage", hisImage);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(new Random().nextInt(85 - 65), builder.build());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createOreoNotification(String title, String message, String hisID, String hisImage, String chatID) {
        Bitmap theBitmap = null;
        try {
            theBitmap = Glide.
                    with(this).
                    load(hisImage).
                    asBitmap().
                    into(-1,-1).
                    get();
        } catch (final ExecutionException e) {

        } catch (final InterruptedException e) {

        }
        NotificationChannel channel = new NotificationChannel(Constants.CHANNEL_ID, "Message", NotificationManager.IMPORTANCE_HIGH);
        channel.setShowBadge(true);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra("hisID", hisID);
        intent.putExtra("hisImage", hisImage);
        intent.putExtra("name", title);
        intent.putExtra("chatID", chatID);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Notification notification = new Notification.Builder(this, Constants.CHANNEL_ID)
                .setShowWhen(true)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(theBitmap)
                .setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        manager.notify(100, notification);

    }
}
