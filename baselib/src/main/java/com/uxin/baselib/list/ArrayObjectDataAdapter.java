package com.uxin.baselib.list;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;

import com.uxin.baselib.list.util.DiffCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArrayObjectDataAdapter extends ObjectDataAdapter
{
    private static final String TAG = "ArrayObjectDataAdapter";
    private final List mItems;
    private final List mOldItems;
    ListUpdateCallback mListUpdateCallback;

    public ArrayObjectDataAdapter(final ViewPresenterSelector presenterSelector) {
        super(presenterSelector);
        this.mItems = new ArrayList();
        this.mOldItems = new ArrayList();
    }

    @Override
    public int size() {
        return this.mItems.size();
    }
    
    @Override
    public Object get(final int index) {
        return this.mItems.get(index);
    }
    
    public int indexOf(final Object item) {
        return this.mItems.indexOf(item);
    }
    
    public void notifyArrayItemRangeChanged(final int positionStart, final int itemCount) {
        this.notifyItemRangeChanged(positionStart, itemCount);
    }
    
    public void add(final Object item) {
        this.mItems.add(item);
    }

    public void addAll(final List items) {
        this.mItems.addAll(items);
        this.notifyItemRangeInserted(items.size(), items.size());
    }

    public void add(final int index, final Object item) {
        this.mItems.add(index, item);
        this.notifyItemRangeInserted(index, 1);
    }
    
    public void addAll(final int index, final Collection items) {
        final int itemsCount = items.size();
        if (itemsCount == 0) {
            return;
        }
        this.mItems.addAll(index, items);
        this.notifyItemRangeInserted(index, itemsCount);
    }
    
    public boolean remove(final Object item) {
        final int index = this.mItems.indexOf(item);
        if (index >= 0) {
            this.mItems.remove(index);
            this.notifyItemRangeRemoved(index, 1);
        }
        return index >= 0;
    }
    
    public void move(final int fromPosition, final int toPosition) {
        if (fromPosition == toPosition) {
            return;
        }
        final Object item = this.mItems.remove(fromPosition);
        this.mItems.add(toPosition, item);
        this.notifyItemMoved(fromPosition, toPosition);
    }
    
    public void replace(final int position, final Object item) {
        this.mItems.set(position, item);
        this.notifyItemRangeChanged(position, 1);
    }
    
    public int removeItems(final int position, final int count) {
        final int itemsToRemove = Math.min(count, this.mItems.size() - position);
        if (itemsToRemove <= 0) {
            return 0;
        }
        for (int i = 0; i < itemsToRemove; ++i) {
            this.mItems.remove(position);
        }
        this.notifyItemRangeRemoved(position, itemsToRemove);
        return itemsToRemove;
    }
    
    public void clear() {
        final int itemCount = this.mItems.size();
        if (itemCount == 0) {
            return;
        }
        this.mItems.clear();
        this.notifyItemRangeRemoved(0, itemCount);
    }
    
    public void setItems(final List itemList, final DiffCallback callback) {
        if (callback == null) {
            this.mItems.clear();
            this.mItems.addAll(itemList);
            this.notifyChanged();
            return;
        }
        this.mOldItems.clear();
        this.mOldItems.addAll(this.mItems);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff((DiffUtil.Callback)new DiffUtil.Callback() {
            public int getOldListSize() {
                return ArrayObjectDataAdapter.this.mOldItems.size();
            }
            
            public int getNewListSize() {
                return itemList.size();
            }
            
            public boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
                return callback.areItemsTheSame(ArrayObjectDataAdapter.this.mOldItems.get(oldItemPosition), itemList.get(newItemPosition));
            }
            
            public boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
                return callback.areContentsTheSame(ArrayObjectDataAdapter.this.mOldItems.get(oldItemPosition), itemList.get(newItemPosition));
            }
            
            @Nullable
            public Object getChangePayload(final int oldItemPosition, final int newItemPosition) {
                return callback.getChangePayload(ArrayObjectDataAdapter.this.mOldItems.get(oldItemPosition), itemList.get(newItemPosition));
            }
        });
        this.mItems.clear();
        this.mItems.addAll(itemList);
        if (this.mListUpdateCallback == null) {
            this.mListUpdateCallback = new ListUpdateCallback() {
                public void onInserted(final int position, final int count) {
                    ArrayObjectDataAdapter.this.notifyItemRangeInserted(position, count);
                }
                
                public void onRemoved(final int position, final int count) {
                    ArrayObjectDataAdapter.this.notifyItemRangeRemoved(position, count);
                }
                
                public void onMoved(final int fromPosition, final int toPosition) {
                    ArrayObjectDataAdapter.this.notifyItemMoved(fromPosition, toPosition);
                }
                
                public void onChanged(final int position, final int count, final Object payload) {
                    ArrayObjectDataAdapter.this.notifyItemRangeChanged(position, count, payload);
                }
            };
        }
        diffResult.dispatchUpdatesTo(this.mListUpdateCallback);
        this.mOldItems.clear();
    }
}