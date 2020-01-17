package com.uxin.baselib.list;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class ViewPresenter {

    public abstract ViewHolder onCreateViewHolder(final ViewGroup p0);

    public abstract void onBindViewHolder(final ViewHolder p0, final Object p1);

    public void onBindViewHolder(final ViewHolder viewHolder, final Object item, final List<Object> payloads) {
        this.onBindViewHolder(viewHolder, item);
    }

    public abstract void onUnbindViewHolder(final ViewHolder p0);

    public void onViewAttachedToWindow(final ViewHolder holder) {
    }

    public void onViewDetachedFromWindow(final ViewHolder holder) {
    }

    public static class ViewHolder
    {
        public final View view;
        public ViewHolder(final View view) {
            this.view = view;
        }

    }
}
