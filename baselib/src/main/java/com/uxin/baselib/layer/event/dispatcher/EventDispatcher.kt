package com.uxin.baselib.layer.event.dispatcher

import com.uxin.baselib.layer.event.EventKey
import com.uxin.baselib.layer.event.resumer.IEventResumer
import com.uxin.baselib.layer.event.resumer.IResumerGroup


internal class EventDispatcher(private val mResumerGroup: IResumerGroup) : IEventDispatcher {

    override fun dispatchLayerEvent(eventCode: EventKey.ExpandKey, jsonEvent: String) {
        mResumerGroup.forEach(object : IResumerGroup.OnLoopListener {
            override fun onEach(receiver: IEventResumer) {
                receiver.onDeliverExpandEvent(eventCode,jsonEvent)
            }

        })
    }

    override fun dispatchNativeEvent(eventCode: EventKey.NativeKey, jsonEvent: String) {
        mResumerGroup.forEach(object : IResumerGroup.OnLoopListener {
            override fun onEach(receiver: IEventResumer) {
                receiver.onDeliverNativeEvent(eventCode,jsonEvent)
            }
        })
    }


    override fun dispatchFilterNativeEvent(
        eventCode: EventKey.NativeKey,
        jsonEvent: String,
        onResumerFilter: IResumerGroup.OnResumerFilter
    ) {
        mResumerGroup.forEach(onResumerFilter,object : IResumerGroup.OnLoopListener {
            override fun onEach(receiver: IEventResumer) {
                receiver.onDeliverNativeEvent(eventCode,jsonEvent)
            }

        })
    }

    override fun dispatchFilterLayerEvent(
        eventCode: EventKey.ExpandKey,
        jsonEvent: String,
        onResumerFilter: IResumerGroup.OnResumerFilter
    ) {
        mResumerGroup.forEach(onResumerFilter,object : IResumerGroup.OnLoopListener {
            override fun onEach(receiver: IEventResumer) {
                receiver.onDeliverExpandEvent(eventCode,jsonEvent)
            }

        })
    }

}