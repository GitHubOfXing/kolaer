package com.uxin.component_service


import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

internal abstract class ArgsBridgeBinder {
    @Throws(
        InvocationTargetException::class,
        IllegalAccessException::class,
        InstantiationException::class
    )
    internal abstract fun apply(vararg args: Any): Any?


    internal class Section(var builder: ArgsBridgeBuilder) : ArgsBridgeBinder() {

        @Throws(
            InvocationTargetException::class,
            IllegalAccessException::class,
            InstantiationException::class
        )
        override fun apply(vararg args: Any): Any? {
            return builder.replaceMethod.invoke(builder.replaceClass.newInstance(), *args)
        }

    }


    internal class ArgsBridgeBuilder(var replaceClass: Class<*>, var replaceMethod: Method)

}