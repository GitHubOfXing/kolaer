package cn.tech.kolaer.platform.page.layer

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import cn.tech.kolaer.R
import cn.tech.kolaer.platform.util.RouterKeyInter
import com.uxin.baselib.cover.BaseCover
import com.uxin.baselib.layer.event.EventKey

class ErrorCover(context: Context) : BaseCover(context) {
    override fun onSubscriberNativeEvent(events: MutableList<EventKey.NativeKey>) {
        events.add(EventKey.NativeKey.NATIVE_CLICK_EVENT)
    }

    override fun onSubscriberExpandEvent(events: MutableList<EventKey.ExpandKey>) {
        events.add(EventKey.ExpandKey.LAYER_LOADING_EVENT)
        events.add(EventKey.ExpandKey.COVER_LOADING_GO)
        events.add(EventKey.ExpandKey.LAYER_NETWORK_EVENT)
    }

    private var mRetry: TextView? = null
    private var mErrorInfo: TextView? = null

    override fun onCreateCover(context: Context): View {
        return View.inflate(context, R.layout.cover_loading, null)
    }

    override fun onCoverDetachedToWindow() {
        mErrorInfo = null
        mRetry = null
    }

    override fun onCoverAttachedToWindow() {
        mErrorInfo = findViewById(R.id.tv_error_info)
        mRetry = findViewById(R.id.tv_retry)

        mRetry?.setOnClickListener {
            onProducerNativeEvent(EventKey.NativeKey.NATIVE_CLICK_EVENT,"重试点击")
        }
    }

    override fun onResumerNativeEvent(events: EventKey.NativeKey, jsonEvent: String) {
        Log.e("chenxing","LoadingCover----ResumerNativeEvent--$events")
    }

    override fun onResumerExpandEvent(events: EventKey.ExpandKey, jsonEvent: String) {
        Log.e("chenxing", "LoadingCover----ResumerExpandEvent--$events")
    }

    override fun coverName(): String {
        return RouterKeyInter.KEY_LOADING_COVER
    }

    override fun getCoverLevel(): Int {
        return 1
    }

}