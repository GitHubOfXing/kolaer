package com.uxin.baselib.layer.event.resumer

import com.uxin.baselib.layer.event.EventKey
import com.uxin.baselib.layer.helper.OnRouterHelperListener
import java.util.*

abstract class BaseEventResumer : IEventResumer {

    private var mResumerGroup: IResumerGroup? = null

    private var layerRouterHelper: OnRouterHelperListener? = null

    /**
     * 订阅相关的事件
     */
    private var mNativeEventKeys: MutableList<EventKey.NativeKey>? = null
    private var mExpandEventKeys: MutableList<EventKey.ExpandKey>? = null

    var mResumerKey: String? = null

    override fun onBindResumerGroup(resumerGroup: IResumerGroup) {
        this.mResumerGroup = resumerGroup

        if(mNativeEventKeys == null) {
            mNativeEventKeys = Collections.synchronizedList(ArrayList());
        }
        onSubscriberNativeEvent(mNativeEventKeys!!)

        if(mExpandEventKeys == null) {
            mExpandEventKeys = Collections.synchronizedList(ArrayList());
        }
        onSubscriberExpandEvent(mExpandEventKeys!!)
    }

    final override fun unBindResumerGroup() {
        this.mResumerGroup = null
        unSubscriberEvent()
    }

    final override fun bindLayerRouterHelper(layerRouterHelper: OnRouterHelperListener) {
        this.layerRouterHelper = layerRouterHelper
    }

    final override fun unbindLayerRouterHelper() {
        this.layerRouterHelper = null
    }

    override fun onProducerNativeEvent(eventKey: EventKey.NativeKey, jsonEvent: String) {
        layerRouterHelper?.onResumerEventByNative(eventKey,jsonEvent)
    }

    override fun onProducerExpandEvent(eventKey: EventKey.ExpandKey, jsonEvent: String) {
        layerRouterHelper?.onResumerEventByExpand(eventKey,jsonEvent)
    }


    final override fun onDeliverExpandEvent(eventKey: EventKey.ExpandKey, jsonEvent: String) {
        mExpandEventKeys?.filter {
            it.value == eventKey.value
        }?.forEach { _ -> onResumerExpandEvent(eventKey,jsonEvent) }
    }

    final override fun onDeliverNativeEvent(eventKey: EventKey.NativeKey, jsonEvent: String) {
        mNativeEventKeys?.filter {
            it.value == eventKey.value
        }?.forEach { _ -> onResumerNativeEvent(eventKey,jsonEvent) }
    }

    abstract fun onResumerNativeEvent(events: EventKey.NativeKey, jsonEvent: String)

    abstract fun onResumerExpandEvent(events: EventKey.ExpandKey, jsonEvent: String)

    abstract fun onSubscriberNativeEvent(events: MutableList<EventKey.NativeKey>)

    abstract fun onSubscriberExpandEvent(events: MutableList<EventKey.ExpandKey>)

    private fun unSubscriberEvent() {
        mNativeEventKeys?.clear()
        mNativeEventKeys = null

        mExpandEventKeys?.clear()
        mExpandEventKeys = null
    }

}