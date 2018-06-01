package com.ccs.app.tradecoin.fcm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ccs.app.tradecoin.config.Debug;
import com.ccs.app.tradecoin.service.NotificationService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "Messaging";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(Debug.TAG + TAG, "From: " + remoteMessage.getFrom());
        Log.d(Debug.TAG + TAG, "MessageId: " + remoteMessage.getMessageId());
        Log.d(Debug.TAG + TAG, "MessageType: " + remoteMessage.getMessageType());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(Debug.TAG + TAG, "Message data payload: " + remoteMessage.getData());

            Bundle bundle = new Bundle();
            for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
                bundle.putString(entry.getKey(), entry.getValue());
            }

            Intent intent = new Intent(getApplicationContext(), NotificationService.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(bundle);
            startService(intent);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(Debug.TAG + TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
