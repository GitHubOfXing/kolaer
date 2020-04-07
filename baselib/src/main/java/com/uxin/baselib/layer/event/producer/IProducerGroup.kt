package com.uxin.baselib.layer.event.producer

interface IProducerGroup {

    fun addEventProducer(producerKey: String,eventProducer: BaseEventProducer)

    fun removeEventProducer(eventProducer: BaseEventProducer): Boolean

    fun destroy()

}