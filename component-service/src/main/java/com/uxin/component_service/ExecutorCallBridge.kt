package com.uxin.component_service


import com.uxin.middleware.IComponentsSection

import java.lang.reflect.Method


internal class ExecutorCallBridge : CallBridge.BeanFactory() {

    override fun get(targetClass: IComponentsSection, method: Method): CallBridge {

        return object : CallBridge {

            override fun componentsSection(): IComponentsSection {
                return targetClass
            }

            /**
             * 获取对应实现method
             * @return
             */
            override fun gain(): Method? {
                try {
                    val clz = targetClass.javaClass
                    return clz.getMethod(method.name, *method.parameterTypes)
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                }

                return null
            }
        }
    }
}