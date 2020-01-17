package com.uxin.logstaticslib

import android.util.Log
import com.uxin.middleware.api.ILogLibApi

class ILogLibApiImpl : ILogLibApi {

    override fun test(url: String) {
        Log.e("chenxing", "----$url")
    }

}