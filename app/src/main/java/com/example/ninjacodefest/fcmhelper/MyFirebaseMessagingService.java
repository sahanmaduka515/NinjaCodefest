package com.example.ninjacodefest.fcmhelper;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d("TAG", "Message Data Placed" + remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null) {
            Toast.makeText(getApplicationContext(), "new message:"+remoteMessage.getNotification().getTitle()+" "+remoteMessage.getNotification().getBody(), Toast.LENGTH_SHORT).show();
            
            
            Log.d("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }
}

