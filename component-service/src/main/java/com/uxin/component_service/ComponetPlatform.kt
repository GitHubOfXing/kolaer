package com.uxin.component_service


import android.text.TextUtils

import com.uxin.middleware.IComponentsSection
import com.uxin.middleware.api.ILogLibApi
import java.util.HashMap

class ComponetPlatform {

    fun registerComponent(unit: ComponentUnit) {

        if (TextUtils.isEmpty(unit.value)) {
            return
        }

        var clzz: Class<*>? = null
        try {
            clzz = Class.forName(unit.value)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        when (unit) {
            ComponetPlatform.ComponentUnit.LOG -> if (components[ILogLibApi::class.java] == null) {
                try {
                    components[ILogLibApi::class.java] = clzz!!.newInstance() as IComponentsSection
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InstantiationException) {
                    e.printStackTrace()
                }

            }
        }
    }

    enum class ComponentUnit private constructor(val value: String) {
        LOG(loglib)
    }

    companion object {

        private val loglib = "com.uxin.logstaticslib.ILogLibApiImpl"

        internal val components: MutableMap<Class<*>, IComponentsSection?> = HashMap()

        init {
            components.put(ILogLibApi::class.java, null)
        }

        @Volatile
        private var instance: ComponetPlatform? = null

        fun getInstance(): ComponetPlatform {
            if (instance == null) {
                synchronized(ComponetPlatform::class.java) {
                    if (instance == null) {
                        instance = ComponetPlatform()
                    }
                }
            }
            return instance!!
        }
    }
}
