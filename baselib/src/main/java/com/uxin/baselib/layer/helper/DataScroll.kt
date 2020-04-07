package com.uxin.baselib.layer.helper

import android.view.MotionEvent
import java.io.Serializable

data class DataScroll (
    val start: MotionEvent,
    val end: MotionEvent,
    val distanceX: Float,
    val distanceY: Float
)