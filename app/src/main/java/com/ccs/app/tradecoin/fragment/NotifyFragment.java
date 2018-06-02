package com.ccs.app.tradecoin.fragment;

import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ccs.app.tradecoin.R;
import com.ccs.app.tradecoin.adapter.NotifyAdapter;
import com.ccs.app.tradecoin.adapter.base.ListAdapter;
import com.ccs.app.tradecoin.db.AppDatabase;
import com.ccs.app.tradecoin.db.dao.NotifyDao;
import com.ccs.app.tradecoin.fragment.base.ListFragment;
import com.ccs.app.tradecoin.item.NotifyItem;
import com.ccs.app.tradecoin.model.NotifyModel;

public class NotifyFragment extends ListFragment<NotifyItem, NotifyModel, NotifyAdapter> {

    private NotifyDao notifyDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notifyDao = AppDatabase.getInstance(getContext()).getNotifyDao();

        setDataSourceFactory(notifyDao.getAllByTime());
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_notify;
    }

    @NonNull
    @Override
    protected NotifyModel onCreateModel(int modelOwner) {
        return getModel(modelOwner, NotifyModel.class);
    }

    @NonNull
    @Override
    protected PagedList.Config getPagedListConfig() {
        return new PagedList.Config.Builder().setPageSize(20).build();
    }

    @NonNull
    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @NonNull
    @Override
    protected NotifyAdapter onCreateListAdapter() {
        return new NotifyAdapter(new ListAdapter.OnItemClickListener<NotifyItem>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, View view, @Nullable NotifyItem item, int position) {
                if (item != null) {
                    item.setRead(true);
                    notifyDao.updateAll(item);
                }
            }
        });
    }

    @Override
    protected int onCreateDivider() {
        return 0;
    }
}
