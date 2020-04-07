package com.uxin.baselib.layer.event.resumer

import com.uxin.baselib.layer.event.EventKey
import com.uxin.baselib.layer.helper.OnRouterHelperListener

interface IEventResumer {

    fun onBindResumerGroup(resumerGroup: IResumerGroup)

    fun unBindResumerGroup()

    /**
     * 生产native事件
     */
    fun onProducerNativeEvent(eventKey: EventKey.NativeKey, jsonEvent : String)

    /**
     * 生产Expand事件
     */
    fun onProducerExpandEvent(eventKey: EventKey.ExpandKey, jsonEvent : String)

    /**
     * 未过滤的消费Expand事件
     */
    fun onDeliverExpandEvent(eventKey: EventKey.ExpandKey, jsonEvent: String)
    /**
     * 未过滤的消费native事件
     */
    fun onDeliverNativeEvent(eventKey: EventKey.NativeKey, jsonEvent: String)

    fun bindLayerRouterHelper(layerRouterHelper: OnRouterHelperListener)

    fun unbindLayerRouterHelper()

}