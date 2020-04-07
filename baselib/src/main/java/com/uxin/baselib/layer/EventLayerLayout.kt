package com.uxin.baselib.layer

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.uxin.baselib.R
import com.uxin.baselib.cover.BaseCover
import com.uxin.baselib.layer.event.dispatcher.DispatcherManager
import com.uxin.baselib.layer.event.producer.BaseEventProducer
import com.uxin.baselib.layer.event.resumer.IEventResumer
import com.uxin.baselib.layer.event.resumer.IResumerGroup
import com.uxin.baselib.layer.helper.LayerTouchHelper

class EventLayerLayout : FrameLayout {

    private lateinit var dispatcher: DispatcherManager
    private lateinit var container: ILayerOpStrategy
    private lateinit var mTouchHelper: LayerTouchHelper

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        init(context, attributeSet)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        initLayerContainer(context)
        dispatcher = DispatcherManager()

        if (attrs != null) {
            val a: TypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.EventLayerLayout
            )
            val mLowerId = a.getResourceId(R.styleable.EventLayerLayout_lowerlayer, 0)
            if(mLowerId > 0) {
                provideLowerLayer(mLowerId)
            }
            val mMediumId = a.getResourceId(R.styleable.EventLayerLayout_mediumlayer,0)
            if(mMediumId > 0) {
                provideMediumLayer(mMediumId)
            }
            val mHigherId = a.getResourceId(R.styleable.EventLayerLayout_higherlayer, 0)
            if(mHigherId > 0) {
                provideHigherLayer(mHigherId)
            }
            a.recycle()
        }
    }

    private fun initLayerContainer(context: Context) {
        container = getLayerStrategy(context)
        if(container.getContainerView() != null) {
            addView(
                container.getContainerView(),
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
    }

    private fun getLayerStrategy(context: Context): ILayerOpStrategy {
        return DefaultLayerContainer(context)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return mTouchHelper?.onTouch(event!!)
    }

    private fun initGesture(context: Context) {
        mTouchHelper = LayerTouchHelper(context, dispatcher)
    }

    fun initEventSource(resumerGroup: IResumerGroup) {
        resumerGroup.forEach(object: IResumerGroup.OnLoopListener {
            override fun onEach(resumer: IEventResumer) {
                if(resumer is BaseCover) {
                    container.addLayer(resumer)
                }
            }

        })
        dispatcher.setResumerGroup(resumerGroup)
        initGesture(context)
    }

    fun initExpandEventSource(producers: Map<String, BaseEventProducer>) {
        dispatcher.inflaterProducer(producers)
    }

    fun provideLowerLayer(@LayoutRes layer: Int) {
        (container as DefaultLayerContainer)?.provideLowerLevelContainer(layer)
    }

    fun provideMediumLayer(@LayoutRes layer: Int) {
        (container as DefaultLayerContainer)?.provideMediumLevelContainer(layer)
    }

    fun provideHigherLayer(@LayoutRes layer: Int) {
        (container as DefaultLayerContainer)?.provideHighLevelContainer(layer)
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        dispatcher?.release()
    }
}