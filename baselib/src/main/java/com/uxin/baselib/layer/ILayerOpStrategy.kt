package com.uxin.baselib.layer

import android.view.ViewGroup
import com.uxin.baselib.cover.BaseCover
import com.uxin.baselib.layer.event.EventKey

interface ILayerOpStrategy {

    fun provideLayerContainer(): ViewGroup

    fun addLayer(layer: BaseCover)

    fun removeLayer(layer: BaseCover)

    fun isContainsCover(layer: BaseCover): Boolean

    fun removeAllLayer()

    fun getLayerCount(): Int

    fun getContainerView(): ViewGroup

//    fun routeInLayerByNative(eventKey: EventKey.NativeKey, jsonEvent: String)
//
//    fun routeInLayerByExpand(eventKey: EventKey.ExpandKey, jsonEvent: String)
}