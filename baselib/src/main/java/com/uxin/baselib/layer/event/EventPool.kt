package com.uxin.baselib.layer.event

import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList

class EventPool {
//    private var mValueMap: MutableMap<String, Any>
//
//    fun subscriberNativeEvent() {
//
//    }
//
//    fun subscriberFrameworkEvent() {
//
//    }
//
//    fun unsubscriberNativeEvent() {
//
//    }
//
//    fun unsubscriberFrameworkEvent() {
//
//    }
//
//    fun putBoolean(key: String, value: Boolean) {
//        put(key, value)
//    }
//
//    fun putInt(key: String, value: Int) {
//        put(key, value)
//    }
//
//    fun putString(key: String, value: String) {
//        put(key, value)
//    }
//
//    fun putFloat(key: String, value: Float) {
//        put(key, value)
//    }
//
//    fun putLong(key: String, value: Long) {
//        put(key, value)
//    }
//
//    fun putDouble(key: String, value: Double) {
//        put(key, value)
//    }
//
//    fun putObject(key: String, value: Any) {
//        put(key, value)
//    }
//
//    fun putBoolean(key: String, value: Boolean, notifyUpdate: Boolean) {
//        put(key, value, notifyUpdate)
//    }
//
//    fun putInt(key: String, value: Int, notifyUpdate: Boolean) {
//        put(key, value, notifyUpdate)
//    }
//
//    fun putString(key: String, value: String, notifyUpdate: Boolean) {
//        put(key, value, notifyUpdate)
//    }
//
//    fun putFloat(key: String, value: Float, notifyUpdate: Boolean) {
//        put(key, value, notifyUpdate)
//    }
//
//    fun putLong(key: String, value: Long, notifyUpdate: Boolean) {
//        put(key, value, notifyUpdate)
//    }
//
//    fun putDouble(key: String, value: Double, notifyUpdate: Boolean) {
//        put(key, value, notifyUpdate)
//    }
//
//    fun putObject(key: String, value: Any, notifyUpdate: Boolean) {
//        put(key, value, notifyUpdate)
//    }
//
//    private fun put(key: String, value: Any) {
//        put(key, value, true)
//    }
//
//    private fun put(key: String, value: Any, notifyUpdate: Boolean) {
//        mValueMap[key] = value
//        if (notifyUpdate)
//            callBackValueUpdate(key, value)
//    }
//
//    private fun callBackValueUpdate(key: String, value: Any) {
//        val mCallbacks = ArrayList<IReceiverGroup.OnGroupValueUpdateListener>()
//        //filter callbacks
//        for (listener in mOnGroupValueUpdateListeners) {
//            if (containsKey(mListenerKeys[listener], key)) {
//                mCallbacks.add(listener)
//            }
//        }
//        //call back
//        for (callback in mCallbacks) {
//            callback.onValueUpdate(key, value)
//        }
//    }
//
//    private fun containsKey(keys: Array<String>?, nowKey: String): Boolean {
//        return if (keys != null && keys.size > 0) {
//            Arrays.binarySearch(keys, nowKey) >= 0
//        } else false
//    }
//
//    operator fun <T> get(key: String): T? {
//        val o = mValueMap[key]
//        try {
//            if (o != null) {
//                return o as T?
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return null
//    }
//
//    fun getBoolean(key: String): Boolean {
//        return getBoolean(key, false)
//    }
//
//    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
//        return get<Boolean>(key) ?: return defaultValue
//    }
//
//    fun getInt(key: String): Int {
//        return getInt(key, 0)
//    }
//
//    fun getInt(key: String, defaultValue: Int): Int {
//        return get<Int>(key) ?: return defaultValue
//    }
//
//    fun getString(key: String): String? {
//        return get<String>(key)
//    }
//
//    fun getFloat(key: String): Float {
//        return getFloat(key, 0f)
//    }
//
//    fun getFloat(key: String, defaultValue: Float): Float {
//        return get<Float>(key) ?: return defaultValue
//    }
//
//    fun getLong(key: String): Long {
//        return getLong(key, 0)
//    }
//
//    fun getLong(key: String, defaultValue: Long): Long {
//        return get<Long>(key) ?: return defaultValue
//    }
//
//    fun getDouble(key: String): Double {
//        return getDouble(key, 0.0)
//    }
//
//    fun getDouble(key: String, defaultValue: Double): Double {
//        return get<Double>(key) ?: return defaultValue
//    }

}