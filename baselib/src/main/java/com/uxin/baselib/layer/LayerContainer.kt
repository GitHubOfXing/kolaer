package com.uxin.baselib.layer

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class LayerContainer : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
    }



}