package com.uxin.baselib.list.util;


import androidx.annotation.NonNull;

public abstract class DiffCallback<Value>
{
    public abstract boolean areItemsTheSame(@NonNull final Value p0, @NonNull final Value p1);
    
    public abstract boolean areContentsTheSame(@NonNull final Value p0, @NonNull final Value p1);
    
    public Object getChangePayload(@NonNull final Value oldItem, @NonNull final Value newItem) {
        return null;
    }
}
