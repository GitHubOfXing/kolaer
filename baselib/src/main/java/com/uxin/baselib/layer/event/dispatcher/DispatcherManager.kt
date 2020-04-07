package com.uxin.baselib.layer.event.dispatcher

import com.uxin.baselib.layer.event.EventKey
import com.uxin.baselib.layer.event.producer.*
import com.uxin.baselib.layer.event.resumer.IEventResumer
import com.uxin.baselib.layer.event.resumer.IResumerGroup

 class DispatcherManager {

    private lateinit var mEventDispatcher: EventDispatcher

    private var mProducerGroup: IProducerGroup? = null

    private var mResumerGroup: IResumerGroup? = null

    private val mEventMessager = object : IEventDeliver {
        override fun produceNativeEvent(eventKey: EventKey.NativeKey, jsonEvent: String) {
            mEventDispatcher.dispatchNativeEvent(eventKey,jsonEvent)
        }

        override fun produceLayerEvent(eventKey: EventKey.ExpandKey, jsonEvent: String) {
            mEventDispatcher.dispatchLayerEvent(eventKey,jsonEvent)
        }

    }

    init {
        mProducerGroup = ProducerGroupAssembly(mEventMessager)
    }


    fun setResumerGroup(resumerGroup: IResumerGroup?) {
        if (resumerGroup == null || resumerGroup == mResumerGroup)
            return

        this.mResumerGroup = resumerGroup


        mEventDispatcher = EventDispatcher(resumerGroup)

    }

     fun inflaterProducer(producers: Map<String, BaseEventProducer>) {
         producers.forEach {
             mProducerGroup?.addEventProducer(it.key,it.value)
         }
     }

    /**
     * native事件触发位
     */
    fun dispatchNativeEvent(eventKey: EventKey.NativeKey, jsonEvent : String) {
        mEventMessager?.produceNativeEvent(eventKey,jsonEvent)
    }

    /**
     * Layer事件触发位
     */
    fun dispatchLayerEvent(eventKey: EventKey.ExpandKey, jsonEvent : String) {
        mEventMessager?.produceLayerEvent(eventKey,jsonEvent)
    }

     fun release() {
         mResumerGroup?.destroy()
         mProducerGroup?.destroy()
     }

 }