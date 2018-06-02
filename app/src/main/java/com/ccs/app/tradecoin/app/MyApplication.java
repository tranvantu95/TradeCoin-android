package com.ccs.app.tradecoin.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.ccs.app.tradecoin.R;
import com.ccs.app.tradecoin.config.AppConfig;
import com.ccs.app.tradecoin.config.Debug;
import com.ccs.app.tradecoin.model.base.SwitchListModel;
import com.ccs.app.tradecoin.utils.General;

public class MyApplication extends Application {

    protected final String TAG = getClass().getSimpleName();

    public static final String DATA = AppConfig.APP_DATA;

    private MediaPlayer mediaPlayer;

    private Handler handler;

    private Runnable runnable;

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
        mediaPlayer = MediaPlayer.create(this, R.raw.alert2);
        mediaPlayer.setLooping(true);
//        playMedia();

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                pauseMedia();
            }
        };
    }

    public void playMedia() {
        mediaPlayer.start();
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 10*60*1000);
    }

    public void pauseMedia() {
        mediaPlayer.pause();
        handler.removeCallbacks(runnable);
    }

}
