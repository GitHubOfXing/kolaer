package com.uxin.baselib.list;

import android.database.Observable;

public abstract class ObjectDataAdapter {

    /**
     * 接收真正adapter的数据变化命令
     */
    private final DataObservable mObservable;
    private ViewPresenterSelector mPresenterSelector;

    public abstract int size();

    public abstract Object get(final int p0);

    public ObjectDataAdapter(ViewPresenterSelector selector) {
        this.mObservable = new DataObservable();
        setPresenterSelector(selector);
    }


    public final void setPresenterSelector(final ViewPresenterSelector presenterSelector) {
        if (presenterSelector == null) {
            throw new IllegalArgumentException("Presenter selector must not be null");
        }
        final boolean update = this.mPresenterSelector != null;
        final boolean selectorChanged = update && this.mPresenterSelector != presenterSelector;
        if (selectorChanged) {
            this.mPresenterSelector = presenterSelector;
        }
        if (update) {
            this.notifyChanged();
        }
    }

    public ViewPresenterSelector getViewPresenterSelector() {
        return mPresenterSelector;
    }

    /**
     * 对类内的数据刷新赋能
     * @param observer
     */
    public final void registerObserver(final DataObserver observer) {
        this.mObservable.registerObserver(observer);
    }

    /**
     * 解绑类内的数据刷新
     * @param observer
     */
    public final void unregisterObserver(final DataObserver observer) {
        this.mObservable.unregisterObserver(observer);
    }

    public final void notifyItemRangeChanged(final int positionStart, final int itemCount) {
        this.mObservable.notifyItemRangeChanged(positionStart, itemCount);
    }

    public final void notifyItemRangeChanged(final int positionStart, final int itemCount, final Object payload) {
        this.mObservable.notifyItemRangeChanged(positionStart, itemCount, payload);
    }

    protected final void notifyItemRangeInserted(final int positionStart, final int itemCount) {
        this.mObservable.notifyItemRangeInserted(positionStart, itemCount);
    }

    protected final void notifyItemRangeRemoved(final int positionStart, final int itemCount) {
        this.mObservable.notifyItemRangeRemoved(positionStart, itemCount);
    }

    protected final void notifyItemMoved(final int fromPosition, final int toPosition) {
        this.mObservable.notifyItemMoved(fromPosition, toPosition);
    }

    protected final void notifyChanged() {
        this.mObservable.notifyChanged();
    }

    public abstract static class DataObserver {

        public void onChanged() {
        }

        public void onItemRangeChanged(final int positionStart, final int itemCount) {
            this.onChanged();
        }

        public void onItemRangeChanged(final int positionStart, final int itemCount, final Object payload) {
            this.onChanged();
        }

        public void onItemRangeInserted(final int positionStart, final int itemCount) {
            this.onChanged();
        }

        public void onItemRangeRemoved(final int positionStart, final int itemCount) {
            this.onChanged();
        }

        public void onItemMoved(final int fromPosition, final int toPosition) {
            this.onChanged();
        }
    }

    private static final class DataObservable extends Observable<DataObserver>
    {
        public void notifyChanged() {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                this.mObservers.get(i).onChanged();
            }
        }

        public void notifyItemRangeChanged(final int positionStart, final int itemCount) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                this.mObservers.get(i).onItemRangeChanged(positionStart, itemCount);
            }
        }

        public void notifyItemRangeChanged(final int positionStart, final int itemCount, final Object payload) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                this.mObservers.get(i).onItemRangeChanged(positionStart, itemCount, payload);
            }
        }

        public void notifyItemRangeInserted(final int positionStart, final int itemCount) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                this.mObservers.get(i).onItemRangeInserted(positionStart, itemCount);
            }
        }

        public void notifyItemRangeRemoved(final int positionStart, final int itemCount) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                this.mObservers.get(i).onItemRangeRemoved(positionStart, itemCount);
            }
        }

        public void notifyItemMoved(final int positionStart, final int toPosition) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                this.mObservers.get(i).onItemMoved(positionStart, toPosition);
            }
        }

        boolean hasObserver() {
            return this.mObservers.size() > 0;
        }
    }
}
