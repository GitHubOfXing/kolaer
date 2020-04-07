package com.uxin.baselib.layer.helper

import com.uxin.baselib.layer.event.EventKey

interface OnRouterHelperListener {
    fun onResumerEventByNative(eventKey: EventKey.NativeKey, jsonEvent: String)
    fun onResumerEventByExpand(eventKey: EventKey.ExpandKey, jsonEvent: String)
}