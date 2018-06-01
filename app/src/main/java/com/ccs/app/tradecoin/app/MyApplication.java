package com.ccs.app.tradecoin.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.ccs.app.tradecoin.config.AppConfig;
import com.ccs.app.tradecoin.config.Debug;
import com.ccs.app.tradecoin.model.base.SwitchListModel;
import com.ccs.app.tradecoin.utils.General;

public class MyApplication extends Application {

    protected final String TAG = getClass().getSimpleName();

    public static final String DATA = AppConfig.APP_DATA;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Debug.TAG + TAG, "onCreate");

        // set support vector drawable for api < 21
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        //
        SharedPreferences sharedPreferences = getSharedPreferences(DATA, MODE_PRIVATE);
        General.typeView = sharedPreferences.getInt(General.TYPE_VIEW, SwitchListModel.LIST);

    }

}
