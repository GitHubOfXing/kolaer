package com.uxin.baselib.layer.helper

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import com.google.gson.Gson
import com.uxin.baselib.layer.event.EventKey
import com.uxin.baselib.layer.event.dispatcher.DispatcherManager

class LayerTouchHelper(
    context: Context,
    private val dispatcher: DispatcherManager
) : OnTouchGestureListener {

    private val gson : Gson = Gson()

    private val mGestureDetector: GestureDetector = GestureDetector(context, BaseGestureCallbackHandler(this))

    fun onTouch(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> onEndGesture(event)
        }
        return mGestureDetector.onTouchEvent(event)
    }


    override fun onSingleTapUp(event: MotionEvent) {
        dispatcher.dispatchNativeEvent(EventKey.NativeKey.NATIVE_TAPUP_EVENT,gson.toJson(event))
    }

    override fun onDoubleTap(event: MotionEvent) {
        dispatcher.dispatchNativeEvent(EventKey.NativeKey.NATIVE_DOUBLE_CLICK_EVENT,gson.toJson(event))
    }

    override fun onDown(event: MotionEvent) {
        dispatcher.dispatchNativeEvent(EventKey.NativeKey.NATIVE_DOUBLE_CLICK_EVENT,gson.toJson(event))
    }

    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float) {
        dispatcher.dispatchNativeEvent(EventKey.NativeKey.NATIVE_DOUBLE_CLICK_EVENT,gson.toJson(DataScroll(e1,e2,distanceX,distanceY)))
    }

    override fun onEndGesture(event: MotionEvent) {
        dispatcher.dispatchNativeEvent(EventKey.NativeKey.NATIVE_DOUBLE_CLICK_EVENT,gson.toJson(event))
    }
}