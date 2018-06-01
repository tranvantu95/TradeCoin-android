package com.ccs.app.tradecoin.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ccs.app.tradecoin.config.AppConfig;
import com.ccs.app.tradecoin.db.dao.NotifyDao;
import com.ccs.app.tradecoin.db.entity.Notify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Notify.class}, version = AppConfig.APP_DATABASE_VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final ExecutorService executor = Executors.newSingleThreadExecutor();

    private static final String DATABASE_NAME = AppConfig.APP_DATABASE;

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if(instance != null) return instance;
        synchronized (AppDatabase.class) {
            if(instance != null) return instance;
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
            return instance;
        }
    }

    public abstract NotifyDao getNotifyDao();
}
