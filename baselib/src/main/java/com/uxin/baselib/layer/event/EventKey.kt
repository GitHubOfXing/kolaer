package com.uxin.baselib.layer.event

class EventKey {

    enum class NativeKey(val value : Int) {
        NATIVE_TOUCH_EVENT(1000),
        NATIVE_CLICK_EVENT(1001),
        NATIVE_DOUBLE_CLICK_EVENT(1002),
        NATIVE_TAPUP_EVENT(1003)
    }

    enum class ExpandKey(val value : Int) {
        LAYER_NETWORK_EVENT(2000),LAYER_LOADING_EVENT(2001),COVER_LOADING_GO(2003)
    }
}