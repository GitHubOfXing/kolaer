package com.uxin.baselib.list;

public abstract class ViewPresenterSelector
{
    public abstract ViewPresenter getPresenter(final Object p0);
    
    public abstract ViewPresenter[] getPresenters();
}