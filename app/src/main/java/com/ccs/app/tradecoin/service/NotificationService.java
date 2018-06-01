package com.ccs.app.tradecoin.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.ccs.app.tradecoin.MainActivity;
import com.ccs.app.tradecoin.R;

public class NotificationService extends Service {

    private MediaPlayer mediaPlayer;
    private boolean mediaReady;

    @Override
    public void onCreate() {
        super.onCreate();



    }

    private void createMedia() {
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaReady = true;
                mediaPlayer.setLooping(true);
            }
        });

        try {
            mediaPlayer.setDataSource(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            mediaPlayer.prepare();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private boolean playMedia() {
        if(!mediaReady) return false;
        mediaPlayer.start();
        return true;
    }

    private void pauseMedia() {
        mediaPlayer.pause();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            showNotification(bundle);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification(Bundle bundle) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(mNotificationManager == null) return;

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(bundle.getString("title"))
                        .setContentText(bundle.getString("message"))
                        .setContentIntent(resultPendingIntent)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000 });

        mBuilder.setLights(Color.BLUE, 1000, 1000);

        // mId allows you to update the notification later on.
        mNotificationManager.notify((int) (Math.random() * Integer.MAX_VALUE), mBuilder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
