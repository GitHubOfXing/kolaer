package cn.tech.kolaer.platform.test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import cn.tech.kolaer.platform.util.NetworkUtils
import com.google.gson.Gson
import com.uxin.baselib.layer.event.EventKey
import com.uxin.baselib.layer.event.producer.BaseEventProducer
import java.lang.ref.WeakReference

class NetworkEventProducer(context: Context) : BaseEventProducer() {

    private val TAG = "NetworkEventProducer"

    private val mAppContext: Context = context.applicationContext

    private var mBroadcastReceiver: NetChangeBroadcastReceiver? = null

    private var mState: Int = 0

    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_CODE_NETWORK_CHANGE -> {
                    val state = msg.obj as Int
                    if (mState == state)
                        return
                    mState = state
                    val sender = getDeliver()
                    if (sender != null) {
                        val gson = Gson()
                        sender!!.produceLayerEvent(EventKey.ExpandKey.LAYER_NETWORK_EVENT, gson.toJson("{'state':'$mState'}"))
                    }
                }
            }
        }
    }

    private fun registerNetChangeReceiver() {
        unregisterNetChangeReceiver()
        if (mAppContext != null) {
            mBroadcastReceiver = NetChangeBroadcastReceiver(mAppContext, mHandler)
            val intentFilter = IntentFilter()
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            mAppContext.registerReceiver(mBroadcastReceiver, intentFilter)
        }
    }

    private fun unregisterNetChangeReceiver() {
        try {
            if (mAppContext != null && mBroadcastReceiver != null) {
                mAppContext.unregisterReceiver(mBroadcastReceiver)
                mBroadcastReceiver = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onBind() {
        mState = NetworkUtils.getNetworkState(mAppContext)
        registerNetChangeReceiver()
    }

    override fun unBind() {
        destroy()
    }

    override fun destroy() {
        if (mBroadcastReceiver != null)
            mBroadcastReceiver!!.destroy()
        unregisterNetChangeReceiver()
        mHandler.removeMessages(MSG_CODE_NETWORK_CHANGE)
    }

    class NetChangeBroadcastReceiver(context: Context, private val handler: Handler) :
        BroadcastReceiver() {

        private val mContextRefer: WeakReference<Context>?

        init {
            mContextRefer = WeakReference(context)
        }

        private val mDelayRunnable = Runnable {
            if (mContextRefer?.get() != null) {
                val networkState = NetworkUtils.getNetworkState(mContextRefer?.get()!!)
                val message = Message.obtain()
                message.what = MSG_CODE_NETWORK_CHANGE
                message.obj = networkState
                handler.sendMessage(message)
            }
        }

        override fun onReceive(context: Context, intent: Intent) {
            Log.e("chenxing","---onReceive--")
            val action = intent.action
            if (ConnectivityManager.CONNECTIVITY_ACTION == action) {
                handler.removeCallbacks(mDelayRunnable)
                handler.postDelayed(mDelayRunnable, 1000)
            }
        }

        fun destroy() {
            handler.removeCallbacks(mDelayRunnable)
        }
    }

    companion object {

        private const val MSG_CODE_NETWORK_CHANGE = 100
    }

}