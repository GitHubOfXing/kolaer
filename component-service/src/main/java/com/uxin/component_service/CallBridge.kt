package com.uxin.component_service


import com.uxin.middleware.IComponentsSection

import java.lang.reflect.Method


interface CallBridge {

    fun componentsSection(): IComponentsSection

    fun gain(): Method?

    abstract class BeanFactory {

        abstract operator fun get(targetClass: IComponentsSection, method: Method): CallBridge
    }
}