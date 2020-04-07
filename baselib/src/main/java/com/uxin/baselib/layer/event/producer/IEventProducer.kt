package com.uxin.baselib.layer.event.producer

interface IEventProducer {

    fun onBind()

    fun unBind()

    fun getDeliver(): IEventDeliver?

    fun destroy()
}