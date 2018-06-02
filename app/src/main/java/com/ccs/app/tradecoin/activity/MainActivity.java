package com.ccs.app.tradecoin.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ccs.app.tradecoin.R;
import com.ccs.app.tradecoin.app.MyApplication;
import com.ccs.app.tradecoin.config.Debug;
import com.ccs.app.tradecoin.db.dao.NotifyDao;
import com.ccs.app.tradecoin.fragment.NotifyFragment;
import com.ccs.app.tradecoin.utils.AppUtils;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        setContentView(R.layout.activity_main);

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(Debug.TAG + TAG, "Token: " + token);

        AppUtils.addFragment(getSupportFragmentManager(), R.id.fragment_container,
                NotifyFragment.class, false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyApplication) getApplication()).pauseMedia();
    }
}
