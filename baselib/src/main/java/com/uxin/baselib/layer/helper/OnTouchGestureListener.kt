package com.uxin.baselib.layer.helper

import android.view.MotionEvent

interface OnTouchGestureListener {
    /**
     * on gesture single tap up
     * @param event
     */
    fun onSingleTapUp(event: MotionEvent)

    /**
     * on gesture double tap
     * @param event
     */
    fun onDoubleTap(event: MotionEvent)

    fun onDown(event: MotionEvent)

    /**
     * on scroll
     * @param e1
     * @param e2
     * @param distanceX
     * @param distanceY
     */
    fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float)

    fun onEndGesture(event: MotionEvent)
}