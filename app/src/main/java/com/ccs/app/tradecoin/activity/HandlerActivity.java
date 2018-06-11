package com.ccs.app.tradecoin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ccs.app.tradecoin.activity.base.BaseActivity;
import com.ccs.app.tradecoin.app.MyApplication;
import com.ccs.app.tradecoin.utils.AppUtils;

public class HandlerActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ((MyApplication) getApplication()).pauseMedia();

        if(isTaskRoot()) {
            Intent intent = new Intent(this, MainActivity.class);
            AppUtils.makeMainIntent(intent);
            startActivity(intent);
        }

        finish();
    }

}
