package com.uxin.baselib.layer.event.producer

import java.util.concurrent.CopyOnWriteArrayList

class ProducerGroupAssembly(private val mProducerDeliver: IEventDeliver) : IProducerGroup {

    private var mEventProducers: MutableList<BaseEventProducer> = CopyOnWriteArrayList()

    override fun addEventProducer(producerKey: String,eventProducer: BaseEventProducer) {
        if (!mEventProducers?.contains(eventProducer)) {
            eventProducer.attachDeliver(mProducerDeliver)
            mEventProducers?.add(eventProducer)
            eventProducer.onBind()
        }
    }

    override fun removeEventProducer(eventProducer: BaseEventProducer): Boolean {
        val remove = mEventProducers?.remove(eventProducer)
        if (eventProducer != null) {
            eventProducer.unBind()
            eventProducer.attachDeliver(null)
        }
        return remove!!
    }

    override fun destroy() {
        for (eventProducer in mEventProducers) {
            eventProducer.unBind()
            eventProducer.destroy()
            eventProducer.attachDeliver(null)
        }
        mEventProducers.clear()
    }

}