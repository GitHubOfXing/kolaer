package com.uxin.baselib.layer.event.resumer

import com.uxin.baselib.layer.helper.OnRouterHelperListener


interface IResumerGroup {

    fun addEventResumer(eventKey: String,eventProducer: BaseEventResumer)

    fun removeEventResumer(eventKey: String)

    fun destroy()

    fun forEach(onLoopListener: OnLoopListener)

    fun forEach(filter: OnResumerFilter, onLoopListener: OnLoopListener)

    fun bindGroupRouterHelper(groupRouterHelper: OnRouterHelperListener)

    interface OnResumerFilter {
        fun filter(resumer: IEventResumer): Boolean
    }

    interface OnLoopListener {
        fun onEach(resumer: IEventResumer)
    }
}