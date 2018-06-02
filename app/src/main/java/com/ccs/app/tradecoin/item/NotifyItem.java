package com.ccs.app.tradecoin.item;

import android.arch.persistence.room.Ignore;

import com.ccs.app.tradecoin.db.entity.Notify;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotifyItem extends Notify implements BaseItem {

    @Ignore
    private String date;

    public String getDate() {
        if(date == null) setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ROOT).format(new Date(getTime())));
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public void setKey(String key) {

    }
}
