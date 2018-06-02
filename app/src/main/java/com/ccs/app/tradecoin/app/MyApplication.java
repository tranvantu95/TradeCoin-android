package com.ccs.app.tradecoin.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.ccs.app.tradecoin.config.AppConfig;
import com.ccs.app.tradecoin.config.Debug;
import com.ccs.app.tradecoin.model.base.SwitchListModel;
import com.ccs.app.tradecoin.utils.General;

public class MyApplication extends Application {

    protected final String TAG = getClass().getSimpleName();

    public static final String DATA = AppConfig.APP_DATA;

    private MediaPlayer mediaPlayer;
    private boolean isPlayMedia;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Debug.TAG + TAG, "onCreate");

        // set support vector drawable for api < 21
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        //
        SharedPreferences sharedPreferences = getSharedPreferences(DATA, MODE_PRIVATE);
        General.typeView = sharedPreferences.getInt(General.TYPE_VIEW, SwitchListModel.LIST);

        //
        createMedia();
    }

    private void createMedia() {
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.setLooping(true);
                if(isPlayMedia) mediaPlayer.start();
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

    public void playMedia() {
        isPlayMedia = true;
        mediaPlayer.start();
    }

    public void pauseMedia() {
        isPlayMedia = false;
        mediaPlayer.pause();
    }

}
