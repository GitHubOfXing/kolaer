package com.uxin.component_service

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.reflect.KClass


class Kolaer(
    private val classes: Map<Class<*>, String>?,
    private val components: ComponetPlatform?,
    private val bridgeFactory: CallBridge.BeanFactory?
) {

    fun <T> create(clazz: Class<T>): T {
        return Proxy.newProxyInstance(clazz.classLoader,
            arrayOf<Class<*>>(clazz), object : InvocationHandler {
                @Throws(Throwable::class)
                override fun invoke(proxy: Any, method: Method, args: Array<Any>): Any? {

                    if (method.declaringClass == Any::class.java) { //avoid proxy the inherited method from Object
                        return method.invoke(this, *args)
                    }

                    val coreService = loadCoreApi(method)
                    val bridgeBinder = CallPowerBinder(coreService, args)
                    return bridgeBinder.call()
                }
            }) as T
    }

    private fun loadCoreApi(method: Method): CoreService? {

        return CoreService.Builder(this, method).build()
    }

    internal fun nextCallBridge(method: Method): CallBridge? {
        val clazz = method.declaringClass
        val targetClass = ComponetPlatform.components.get(clazz)
        return if (targetClass != null) {
            bridgeFactory?.get(targetClass!!, method)
        } else {
            null
        }
    }


    /**
     * 存放静态组件注册
     */
    class Builder {
        private var classes: Map<Class<*>, String>? = null
        private var components: ComponetPlatform? = null
        private var bridgeFactory: CallBridge.BeanFactory? = null

        fun registClasses(classes: Map<Class<*>, String>): Builder {
            this.classes = classes
            return this
        }

        fun dynamicRegist(components: ComponetPlatform): Builder {
            this.components = components
            return this
        }

        fun build(): Kolaer {
            bridgeFactory = ExecutorCallBridge()
            return Kolaer(classes, components, bridgeFactory)
        }
    }
}
