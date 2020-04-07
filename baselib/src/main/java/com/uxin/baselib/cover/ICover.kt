package com.uxin.baselib.cover

import android.view.View
import com.uxin.baselib.layer.helper.OnRouterHelperListener

interface ICover {

    companion object {
        val LEVEL_MAX: Int
            get() = 1 shl 5

        //level low container start value.
        val COVER_LEVEL_LOW: Int
            get() = 0

        //level medium container start value.
        val COVER_LEVEL_MEDIUM: Int
            get() = 1 shl 5

        //level high container start value.
        val COVER_LEVEL_HIGH: Int
            get() = 1 shl 6
    }


    fun setCoverVisibility(visibility: Int)
    fun getView(): View
    fun getCoverLevel(): Int
    fun coverName(): String
}