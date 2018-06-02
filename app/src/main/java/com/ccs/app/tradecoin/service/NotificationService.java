package com.ccs.app.tradecoin.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.ccs.app.tradecoin.activity.HandlerActivity;
import com.ccs.app.tradecoin.activity.MainActivity;
import com.ccs.app.tradecoin.R;
import com.ccs.app.tradecoin.app.MyApplication;
import com.ccs.app.tradecoin.db.AppDatabase;
import com.ccs.app.tradecoin.db.dao.NotifyDao;
import com.ccs.app.tradecoin.db.entity.Notify;

import java.util.Date;

public class NotificationService extends Service {

    private NotifyDao notifyDao;

    @Override
    public void onCreate() {
        super.onCreate();
        notifyDao = AppDatabase.getInstance(this).getNotifyDao();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            String title = bundle.getString("title");
            String message = bundle.getString("message");
            long time = bundle.getLong("time", System.currentTimeMillis());

            saveNotify(title, message, time);
            showNotification(title, message, time);

            ((MyApplication) getApplication()).playMedia();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void saveNotify(String title, String message, long time) {
        Notify notify = new Notify();
        notify.setTitle(title);
        notify.setMessage(message);
        notify.setTime(time);
        notify.setRead(false);
        notifyDao.insertAll(notify);
    }

    private void showNotification(String title, String message, long time) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(mNotificationManager == null) return;

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, HandlerActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String chanelId = "com.ccs.app.tradecoin";
        String chanelName = "Trade Coin";
        String channelDescription = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(chanelId, chanelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channelDescription);
            mNotificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, chanelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(resultPendingIntent)
                .setWhen(time)
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

//        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
//        mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000 });
//        mBuilder.setLights(Color.BLUE, 1000, 1000);

        long[] vibrate = new long[10 * 60];
        for(int i = 0; i < vibrate.length; i++) vibrate[i] = 1000;
        mBuilder.setVibrate(vibrate);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(1001, mBuilder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
