package com.uxin.baselib.layer

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.FrameLayout
import com.uxin.baselib.cover.BaseCover

abstract class AbsLayerContainer( val context: Context) : ILayerOpStrategy {

    private val layers: MutableList<BaseCover> = ArrayList()

    protected var layerContainer: ViewGroup

    init {
        layerContainer = provideLayerContainer()
    }

    final override fun provideLayerContainer(): ViewGroup {
        layerContainer = FrameLayout(context)
        layerContainer.setBackgroundColor(Color.TRANSPARENT)
        return layerContainer
    }

    override fun addLayer(layer: BaseCover) {
        addLayerBefore(layer)
        if (isAvailableLayer(layer)) {
            layers.add(layer)
            onLayerContainerAdd(layer)
        }
    }

    override fun removeLayer(layer: BaseCover) {
        removeLayerBefore(layer)
        if (isAvailableLayer(layer)) {
            layers.remove(layer)
            onLayerContainerRemove(layer)
        }
    }

    abstract fun addLayerBefore(layer: BaseCover)

    abstract fun removeLayerBefore(layer: BaseCover)

    abstract fun onLayerContainerRemove(layer: BaseCover)

    abstract fun onLayerContainerAdd(layer: BaseCover)

    abstract fun isAvailableLayer(layer: BaseCover?): Boolean

    override fun isContainsCover(cover: BaseCover): Boolean {
        if (!isAvailableLayer(cover))
            return false
        val index = rootIndexOfChild(cover.getView())
        if (index != -1) {
            return true
        }
        val childCount = getLayerCount()
        if (childCount <= 0)
            return false
        var result = false
        for (i in 0 until childCount) {
            val view = layers?.get(i)
            if (view is ViewGroup && (view as ViewGroup).indexOfChild(cover.getView()) != -1) {
                result = true
                break
            }
        }
        return result
    }

    protected fun addLevelLayer(
        container: ViewStub,
        layoutParams: ViewGroup.LayoutParams?
    ) {
        var layoutParams = layoutParams
        if (layoutParams == null)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        layerContainer?.addView(container,layoutParams)
    }

    protected fun rootIndexOfChild(view: View): Int {
        return layerContainer?.indexOfChild(view)
    }

    override fun removeAllLayer() {
        layers.clear()
    }

    override fun getLayerCount(): Int {
        return layers?.size ?: 0
    }

    override fun getContainerView(): ViewGroup {
        return layerContainer
    }

}