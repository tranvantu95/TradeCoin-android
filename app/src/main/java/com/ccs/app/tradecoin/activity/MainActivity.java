package com.ccs.app.tradecoin.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ccs.app.tradecoin.R;
import com.ccs.app.tradecoin.activity.base.BaseActivity;
import com.ccs.app.tradecoin.app.MyApplication;
import com.ccs.app.tradecoin.config.Debug;
import com.ccs.app.tradecoin.db.dao.NotifyDao;
import com.ccs.app.tradecoin.fragment.NotifyFragment;
import com.ccs.app.tradecoin.utils.AppUtils;
import com.ccs.app.tradecoin.utils.KeyUtils;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends BaseActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        setContentView(R.layout.activity_main);

        Log.d(Debug.TAG + TAG, "SHA1: " + KeyUtils.getSHA1(this));
        Log.d(Debug.TAG + TAG, "Token: " + FirebaseInstanceId.getInstance().getToken());

        AppUtils.addFragment(getSupportFragmentManager(), R.id.fragment_container,
                NotifyFragment.class, false);
    }

    private void showSHA1Dialog() {
        showDialog("SHA1", KeyUtils.getSHA1(this));
    }

    private void showTokenDialog() {
        showDialog("Token", FirebaseInstanceId.getInstance().getToken());
    }

    private void showDialog(String title, final String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("Copy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppUtils.copyText(getApplicationContext(), message);
            }
        });

        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_sha1:
                showSHA1Dialog();
                return true;

            case R.id.action_show_token:
                showTokenDialog();
                return true;

            case R.id.action_pause_music:
                ((MyApplication) getApplication()).pauseMedia();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
