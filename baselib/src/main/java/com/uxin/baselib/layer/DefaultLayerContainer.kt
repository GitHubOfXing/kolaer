package com.uxin.baselib.layer

import android.content.Context
import android.view.ViewGroup
import android.view.ViewStub
import androidx.annotation.LayoutRes
import com.uxin.baselib.cover.BaseCover
import com.uxin.baselib.cover.ICover
import com.uxin.baselib.util.LoaderConfig
import java.lang.RuntimeException

class DefaultLayerContainer(context: Context) : AbsLayerContainer(context) {

    private lateinit var mlevelLower: ViewStub
    private lateinit var mlevelMedium: ViewStub
    private lateinit var mlevleHigh: ViewStub

    private lateinit var mlevelLowerContainer: ViewGroup
    private lateinit var mlevelMediumContainer: ViewGroup
    private lateinit var mlevelHighContainer: ViewGroup

    init {
        initLevelLayers()
    }

    override fun addLayerBefore(layer: BaseCover) {

    }

    override fun removeLayerBefore(layer: BaseCover) {
    }

    override fun onLayerContainerRemove(layer: BaseCover) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLayerContainerAdd(layer: BaseCover) {
        val level = layer.getCoverLevel()
        when {
            level < ICover.COVER_LEVEL_LOW -> mlevelLowerContainer?.addView(layer.getView(),inflateContainerLayoutParams())
            level < ICover.COVER_LEVEL_MEDIUM -> mlevelMediumContainer?.addView(layer.getView(),inflateContainerLayoutParams())
            else -> mlevelHighContainer?.addView(layer.getView(),inflateContainerLayoutParams())
        }
    }

    override fun isAvailableLayer(layer: BaseCover?): Boolean {
        return layer?.getView() != null
    }

    open fun provideLowerLevelContainer(@LayoutRes layoutRes : Int) {
        mlevelLower?.layoutResource = layoutRes
        val tmp = mlevelLower?.inflate()
        if(tmp is ViewGroup) {
            mlevelLowerContainer = tmp
        } else {
            if(LoaderConfig.configDebug) {
                throw RuntimeException("the lower-level is not ViewGroup,provide levelcontainer must be ViewGroup")
            }
        }
    }

    open fun provideMediumLevelContainer(@LayoutRes layoutRes : Int) {
        mlevelMedium?.layoutResource = layoutRes
        val tmp = mlevelMedium?.inflate()
        if(tmp is ViewGroup) {
            mlevelMediumContainer = tmp
        } else {
            if(LoaderConfig.configDebug) {
                throw RuntimeException("the medium-level is not ViewGroup,provide levelcontainer must be ViewGroup")
            }
        }
    }

    open fun provideHighLevelContainer(@LayoutRes layoutRes : Int) {
        mlevleHigh?.layoutResource = layoutRes
        val tmp = mlevleHigh?.inflate()
        if(tmp is ViewGroup) {
            mlevelHighContainer = tmp
        } else {
            if(LoaderConfig.configDebug) {
                throw RuntimeException("the high-level is not ViewGroup,provide levelcontainer must be ViewGroup")
            }
        }
    }

    private fun initLevelLayers() {
        mlevelLower = ViewStub(context)
        mlevelMedium = ViewStub(context)
        mlevleHigh = ViewStub(context)
        addLevelLayer(mlevelLower, null)
        addLevelLayer(mlevelMedium, null)
        addLevelLayer(mlevleHigh, null)
    }

    private fun inflateContainerLayoutParams(): ViewGroup.LayoutParams {
        return ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
}