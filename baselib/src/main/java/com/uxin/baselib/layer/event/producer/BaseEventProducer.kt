package com.uxin.baselib.layer.event.producer

abstract class BaseEventProducer : IEventProducer {
    private var mReceiverEventSender: IEventDeliver? = null

    internal fun attachDeliver(receiverEventSender: IEventDeliver?) {
        this.mReceiverEventSender = receiverEventSender
    }

    override fun getDeliver(): IEventDeliver? {
        return mReceiverEventSender
    }
}