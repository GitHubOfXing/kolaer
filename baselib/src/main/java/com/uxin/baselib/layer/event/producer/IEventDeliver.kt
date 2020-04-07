package com.uxin.baselib.layer.event.producer

import com.uxin.baselib.layer.event.EventKey

interface IEventDeliver {
    fun produceNativeEvent(eventKey: EventKey.NativeKey, jsonEvent: String)

    fun produceLayerEvent(eventKey: EventKey.ExpandKey, jsonEvent: String)
}