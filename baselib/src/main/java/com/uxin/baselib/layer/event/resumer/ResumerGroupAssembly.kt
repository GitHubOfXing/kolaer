package com.uxin.baselib.layer.event.resumer

import com.uxin.baselib.cover.BaseCover
import com.uxin.baselib.layer.event.EventKey
import com.uxin.baselib.layer.helper.OnRouterHelperListener
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class ResumerGroupAssembly : IResumerGroup {

    private val mResumers: MutableMap<String,IEventResumer> = ConcurrentHashMap(16)
    private val mResumerArray: MutableList<IEventResumer> =
        Collections.synchronizedList(ArrayList())

    private var groupRouterHelper: OnRouterHelperListener? = null

    /**
     * 组内处理的事件  好比增减组内成员/组间沟通
     */
    private val routerHelper: OnRouterHelperListener = object: OnRouterHelperListener {

        override fun onResumerEventByNative(eventKey: EventKey.NativeKey, jsonEvent: String) {
            groupRouterHelper?.onResumerEventByNative(eventKey,jsonEvent)
            forEach(object: IResumerGroup.OnLoopListener {
                override fun onEach(resumer: IEventResumer) {
                    resumer.onDeliverNativeEvent(eventKey,jsonEvent)
                }
            })
        }

        override fun onResumerEventByExpand(eventKey: EventKey.ExpandKey, jsonEvent: String) {
            groupRouterHelper?.onResumerEventByExpand(eventKey,jsonEvent)
            forEach(object: IResumerGroup.OnLoopListener {
                override fun onEach(resumer: IEventResumer) {
                    resumer.onDeliverExpandEvent(eventKey, jsonEvent)
                }
            })
        }

    }

    override fun addEventResumer(eventKey: String,eventResumer: BaseEventResumer) {
        if(!mResumers.containsKey(eventKey)) {
            eventResumer.mResumerKey = eventKey
            eventResumer.onBindResumerGroup(this)
            eventResumer.bindLayerRouterHelper(routerHelper)
            mResumers[eventKey] = eventResumer
            mResumerArray.add(eventResumer)
        }
    }

    override fun removeEventResumer(eventKey: String) {
        val resumer = mResumers[eventKey]
        resumer?.unbindLayerRouterHelper()
        resumer?.unBindResumerGroup()
        mResumers.remove(eventKey)
    }

    override fun forEach(onLoopListener: IResumerGroup.OnLoopListener) {
        mResumerArray.sortWith(Comparator<Any> { o1, o2 ->
            var x = 0
            var y = 0
            if (o1 is BaseCover) {
                x = o1.getCoverLevel()
            }
            if (o2 is BaseCover) {
                y = o2.getCoverLevel()
            }
            if (x < y) -1 else if (x == y) 0 else 1
        })
        for (resumer in mResumerArray) {
            onLoopListener.onEach(resumer)
        }
    }

    override fun forEach(
        filter: IResumerGroup.OnResumerFilter,
        onLoopListener: IResumerGroup.OnLoopListener
    ) {

    }

    override fun bindGroupRouterHelper(groupRouterHelper: OnRouterHelperListener) {
        this.groupRouterHelper = groupRouterHelper
    }

    override fun destroy() {
        mResumerArray.forEach {
            it.unBindResumerGroup()
            it.unbindLayerRouterHelper()
        }
        mResumerArray.clear()
        mResumers.clear()
        this.groupRouterHelper = null
    }

}