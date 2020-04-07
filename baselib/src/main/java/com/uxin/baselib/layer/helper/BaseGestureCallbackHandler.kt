package com.uxin.baselib.layer.helper

import android.view.GestureDetector
import android.view.MotionEvent

class BaseGestureCallbackHandler(private var mOnTouchGestureListener: OnTouchGestureListener?) :
    GestureDetector.SimpleOnGestureListener() {

    private val TAG = "GestureCallbackHandler"

    private var mGestureEnable = true
    private var mGestureScrollEnable = true

    fun setGestureEnable(enable: Boolean) {
        this.mGestureEnable = enable
    }

    fun setGestureScrollEnable(gestureScrollEnable: Boolean) {
        this.mGestureScrollEnable = gestureScrollEnable
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        if (mOnTouchGestureListener != null) {
            mOnTouchGestureListener!!.onSingleTapUp(e)
        }
        return super.onSingleTapUp(e)
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        if (mOnTouchGestureListener != null) {
            mOnTouchGestureListener!!.onDoubleTap(e)
        }
        return super.onDoubleTap(e)
    }

    override fun onDown(e: MotionEvent): Boolean {
        if (mOnTouchGestureListener != null) {
            mOnTouchGestureListener!!.onDown(e)
        }
        return mGestureEnable
    }

    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if (mOnTouchGestureListener != null && mGestureScrollEnable) {
            mOnTouchGestureListener!!.onScroll(e1, e2, distanceX, distanceY)
        }
        return super.onScroll(e1, e2, distanceX, distanceY)
    }
}