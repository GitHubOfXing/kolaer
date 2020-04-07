package com.uxin.baselib.cover

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.IntRange
import com.uxin.baselib.cover.ICover.Companion.LEVEL_MAX
import com.uxin.baselib.layer.event.EventKey
import com.uxin.baselib.layer.event.resumer.BaseEventResumer
import com.uxin.baselib.layer.helper.OnRouterHelperListener

abstract class BaseCover(context: Context) : BaseEventResumer(), ICover, View.OnAttachStateChangeListener {

    private val mCoverView :View

    init {
        mCoverView = onCreateCover(context)
        mCoverView.addOnAttachStateChangeListener(this)
    }

    override fun setCoverVisibility(visibility: Int) {
        mCoverView.visibility = visibility
    }

    override fun getView(): View {
        return mCoverView
    }

    protected fun <T : View> findViewById(@IdRes viewId: Int): T? {
        return mCoverView.findViewById(viewId) as T?
    }

    override fun onViewDetachedFromWindow(v: View?) {
        onCoverDetachedToWindow()
    }

    override fun onViewAttachedToWindow(v: View?) {
        onCoverAttachedToWindow()
    }

    protected abstract fun onCreateCover(context: Context): View
    protected abstract fun onCoverDetachedToWindow()
    protected abstract fun onCoverAttachedToWindow()

    override fun getCoverLevel(): Int {
        return ICover.COVER_LEVEL_LOW
    }

    @IntRange(from = 0, to = 31)
    protected fun levelLow(priority: Int): Int {
        return levelPriority(ICover.COVER_LEVEL_LOW, priority)
    }

    @IntRange(from = 0, to = 31)
    protected fun levelMedium(priority: Int): Int {
        return levelPriority(ICover.COVER_LEVEL_MEDIUM, priority)
    }

    @IntRange(from = 0, to = 31)
    protected fun levelHigh( priority: Int): Int {
        return levelPriority(ICover.COVER_LEVEL_HIGH, priority)
    }

    private fun levelPriority(level: Int, priority: Int): Int {
        return level + priority % LEVEL_MAX
    }

}