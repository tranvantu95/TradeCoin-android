package com.ccs.app.tradecoin.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.View;
import android.widget.TextView;

import com.ccs.app.tradecoin.R;
import com.ccs.app.tradecoin.adapter.base.ListAdapter;
import com.ccs.app.tradecoin.item.NotifyItem;

import java.util.Date;

public class NotifyAdapter extends ListAdapter<NotifyItem, NotifyAdapter.ViewHolder> {

    public NotifyAdapter(@NonNull OnItemClickListener onItemClickListener) {
        super(PAGED_LIST_ADAPTER_MODE, onItemClickListener, createDiffCallback(NotifyItem.class));
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_notify;
    }

    @NonNull
    @Override
    protected ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(this, view);
    }

    public static class ViewHolder extends ListAdapter.ViewHolder<NotifyItem, NotifyAdapter> {

        private TextView title, message, time;

        public ViewHolder(NotifyAdapter adapter, View itemView) {
            super(adapter, itemView);

            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
        }

        @Override
        protected void updateItem(@NonNull NotifyItem notifyItem, int position) {
            title.setText(notifyItem.getTitle());
            message.setText(notifyItem.getMessage());
            time.setText(notifyItem.getDate());

            int color = notifyItem.isRead() ? Color.GRAY : Color.BLACK;

            title.setTextColor(color);
            message.setTextColor(color);
            time.setTextColor(color);
        }

        @Override
        protected void clearItem(int position) {

        }
    }
}
