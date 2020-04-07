package com.uxin.baselib.layer.event.dispatcher

import com.uxin.baselib.layer.event.EventKey
import com.uxin.baselib.layer.event.resumer.IResumerGroup


internal interface IEventDispatcher {

    fun dispatchNativeEvent(eventCode: EventKey.NativeKey, jsonEvent: String)

    fun dispatchFilterNativeEvent(
        eventCode: EventKey.NativeKey,
        jsonEvent: String,
        onResumerFilter: IResumerGroup.OnResumerFilter
    )

    fun dispatchLayerEvent(eventCode: EventKey.ExpandKey, jsonEvent: String)

    fun dispatchFilterLayerEvent(
        eventCode: EventKey.ExpandKey,
        jsonEvent: String,
        onResumerFilter: IResumerGroup.OnResumerFilter
    )

}