package com.uxin.baselib.list;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


/**
 * create by lichenxing
 * 将RecyclerView的Adapter功能拆分成两部分
 */
public class BridgeAdapter extends RecyclerView.Adapter {

    /**
     * 盛放数据逻辑
     */
    private ObjectDataAdapter mAdapter;
    /**
     * 盛放数据与itemView之间的关联关系
     */
    private ViewPresenterSelector mPresenterSelector;
    /**
     * 盛放itemView逻辑和itemView的layouts
     */
    private SparseArray<ViewPresenter> mPresenters;

    /**
     * 传递adapter数据刷新功能进ObjectDataAdapter(数据逻辑层)
     */
    private ObjectDataAdapter.DataObserver mDataObserver;


    public BridgeAdapter(final ObjectDataAdapter dataAdapter, ViewPresenterSelector mPresenterSelector) {
        this.mPresenters = new SparseArray<>();
        this.mDataObserver = new ObjectDataAdapter.DataObserver() {
            @Override
            public void onChanged() {
                BridgeAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(final int positionStart, final int itemCount) {
                BridgeAdapter.this.notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeChanged(final int positionStart, final int itemCount, final Object payload) {
                BridgeAdapter.this.notifyItemRangeChanged(positionStart, itemCount, payload);
            }

            @Override
            public void onItemRangeInserted(final int positionStart, final int itemCount) {
                BridgeAdapter.this.notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeRemoved(final int positionStart, final int itemCount) {
                BridgeAdapter.this.notifyItemRangeRemoved(positionStart, itemCount);
            }

            @Override
            public void onItemMoved(final int fromPosition, final int toPosition) {
                BridgeAdapter.this.notifyItemMoved(fromPosition, toPosition);
            }
        };
        this.setAdapter(dataAdapter);
        this.mPresenterSelector = mPresenterSelector;
    }


    public void setAdapter(ObjectDataAdapter adapter) {
        if (adapter == this.mAdapter) {
            return;
        }
        if (this.mAdapter != null) {
            this.mAdapter.unregisterObserver(this.mDataObserver);
        }
        this.mAdapter = adapter;
        if (this.mAdapter == null) {
            this.notifyDataSetChanged();
            return;
        }
        this.mAdapter.registerObserver(this.mDataObserver);
        this.notifyDataSetChanged();
    }



    public int getItemCount() {
        return (this.mAdapter != null) ? this.mAdapter.size() : 0;
    }


    public int getItemViewType(final int position) {
        final ViewPresenterSelector presenterSelector = (this.mPresenterSelector != null) ? this.mPresenterSelector : this.mAdapter.getViewPresenterSelector();
        final Object item = this.mAdapter.get(position);
        final ViewPresenter presenter = presenterSelector.getPresenter(item);
        int type = this.mPresenters.indexOfValue(presenter);
        if (type < 0) {
            this.mPresenters.append(this.mPresenters.size(),presenter);
            type = this.mPresenters.indexOfValue(presenter);
        }
        return type;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ViewPresenter presenter = this.mPresenters.get(viewType);
        View view;
        ViewPresenter.ViewHolder presenterVh;
        presenterVh = presenter.onCreateViewHolder(parent);
        view = presenterVh.view;
        final ViewHolder viewHolder = new ViewHolder(presenter, view, presenterVh);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
        final ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.mItem = this.mAdapter.get(position);
        viewHolder.mPresenter.onBindViewHolder(viewHolder.mHolder, viewHolder.mItem, payloads);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.mItem = this.mAdapter.get(position);
        viewHolder.mPresenter.onBindViewHolder(viewHolder.mHolder, viewHolder.mItem);
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        final ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.mPresenter.onUnbindViewHolder(viewHolder.mHolder);
        viewHolder.mItem = null;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        final ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.mPresenter.onUnbindViewHolder(viewHolder.mHolder);
        viewHolder.mItem = null;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        final ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.mPresenter.onViewDetachedFromWindow(viewHolder.mHolder);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder
    {
        final ViewPresenter mPresenter;
        final ViewPresenter.ViewHolder mHolder;
        Object mItem;

        public final ViewPresenter getPresenter() {
            return this.mPresenter;
        }

        public final ViewPresenter.ViewHolder getViewHolder() {
            return this.mHolder;
        }

        public final Object getItem() {
            return this.mItem;
        }

        ViewHolder(final ViewPresenter presenter, final View view, final ViewPresenter.ViewHolder holder) {
            super(view);
            this.mPresenter = presenter;
            this.mHolder = holder;
        }
    }
}
