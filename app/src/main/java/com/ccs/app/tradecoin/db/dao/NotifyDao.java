package com.ccs.app.tradecoin.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.ccs.app.tradecoin.config.Debug;
import com.ccs.app.tradecoin.db.AppDatabase;
import com.ccs.app.tradecoin.db.entity.Notify;
import com.ccs.app.tradecoin.item.NotifyItem;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Dao
public abstract class NotifyDao implements BaseDao<Notify> {

    protected final String TAG = getClass().getSimpleName();

    private static final ExecutorService executor = AppDatabase.executor;

    @MainThread
    @Query("SELECT * FROM Notify ORDER BY id DESC")
    public abstract DataSource.Factory<Integer, NotifyItem> getAllDESC();

    @WorkerThread
    @Query("SELECT * FROM Notify")
    public abstract List<NotifyItem> _getAll();

    @WorkerThread
    @Query("SELECT COUNT(*) FROM Notify")
    public abstract int _getCount();

    @WorkerThread
    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    public abstract Long[] _insertAll(Notify... notifies);

    @WorkerThread
    @Update
    public abstract void _updateAll(Notify... notifies);

    @WorkerThread
    @Delete
    public abstract void _deleteAll(Notify... notifies);

    //
    @MainThread
    public LiveData<List<NotifyItem>> getAll() {
        final MutableLiveData<List<NotifyItem>> notes = new MutableLiveData<>();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                notes.postValue(_getAll());
            }
        });
        return notes;
    }

    @MainThread
    public LiveData<Integer> getCount() {
        final MutableLiveData<Integer> count = new MutableLiveData<>();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                count.postValue(_getCount());
            }
        });
        return count;
    }

    //
    @MainThread
    public LiveData<List<Long>> insertAll(final Notify... notifies) {
        Log.d(Debug.TAG + TAG, "insertAll");
        final MutableLiveData<List<Long>> ids = new MutableLiveData<>();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Long[] _ids = _insertAll(notifies);
                int i = 0;
                for(long id : _ids) {
                    notifies[i].setId(id);
                    i++;
                }
                ids.postValue(Arrays.asList(_ids));
            }
        });
        return ids;
    }

    @MainThread
    public void updateAll(final Notify... notifies) {
        Log.d(Debug.TAG + TAG, "updateAll");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                _updateAll(notifies);
            }
        });
    }

    @MainThread
    public void deleteAll(final Notify... notifies) {
        Log.d(Debug.TAG + TAG, "deleteAll");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                _deleteAll(notifies);
            }
        });
    }
}
