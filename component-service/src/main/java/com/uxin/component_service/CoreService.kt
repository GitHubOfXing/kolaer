package com.uxin.component_service

import android.text.TextUtils
import android.util.Log

import com.uxin.middleware.annotation.Rely
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.HashMap

internal class CoreService(builder: Builder) {

    private val method: Method?
    private val bridge: CallBridge?
    private val binders: Map<String, ArgsBridgeBinder>


    init {
        method = builder.method
        binders = builder.binders
        bridge = builder.bridge
    }

    fun coreBuilt(vararg args: Any): Any? {

        if (method == null) {
            return null
        }

        if (binders != null) {
            val replace = binders[method.name]
            try {
                if(replace != null) {
                    return replace?.apply(*args)
                }
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                throw methodError("must provide the default constructor.", method)
            }

        }
        if (bridge != null) {
            val target = bridge.gain()
            try {
                if(target != null) {
                    return target?.invoke(bridge.componentsSection(), *args)
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }

        }
        return null
    }

    internal class Builder(private val kolaer: Kolaer, internal val method: Method?) {
        internal var bridge: CallBridge? = null
        internal val binders: MutableMap<String, ArgsBridgeBinder>

        init {
            binders = HashMap()
        }

        fun build(): CoreService? {

            if (method == null) {
                return null
            }

            if (binders[method.name] == null) {
                if (method.annotations.isNotEmpty()) {
                    for (annotation in method.annotations) {
                        val binder = parseMethodAnnotation(annotation, method.parameterTypes)
                        if (binder != null) {
                            binders[method.name] = binder
                        }
                    }
                }
            }

            bridge = kolaer.nextCallBridge(method) as CallBridge

            return CoreService(this)
        }

        private fun parseMethodAnnotation(
            annotation: Annotation,
            parameterTypes: Array<Class<*>>
        ): ArgsBridgeBinder? {
            var binder: ArgsBridgeBinder? = null
            if (annotation is Rely) {
                val methodName = annotation.methodName
                val className = annotation.className
                if (TextUtils.isEmpty(methodName) || TextUtils.isEmpty(className)) {
                    return null
                } else {
                    try {
                        val clazz = Class.forName(className)
                        val replaceMethod = clazz.getDeclaredMethod(methodName, *parameterTypes)
                        if (replaceMethod != null) {
                            val builder = ArgsBridgeBinder.ArgsBridgeBuilder(clazz, replaceMethod)
                            binder = ArgsBridgeBinder.Section(builder)
                        }
                    } catch (e: NoSuchMethodException) {
                        e.printStackTrace()
                    } catch (e: ClassNotFoundException) {
                        e.printStackTrace()
                    }

                }
            }

            return binder
        }

    }


    private fun methodError(message: String, vararg args: Any): RuntimeException {
        return methodError(null, message, *args)
    }

    private fun methodError(
        cause: Throwable?,
        message: String,
        vararg args: Any
    ): RuntimeException {
        var message = message
        message = String.format(message, *args)
        return IllegalArgumentException(
            message
                    + "\n    for method "
                    + method!!.declaringClass.simpleName
                    + "."
                    + method.name, cause
        )
    }
}
