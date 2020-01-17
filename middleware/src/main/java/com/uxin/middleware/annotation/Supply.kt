package com.uxin.middleware.annotation

import kotlin.reflect.KClass

@MustBeDocumented
@kotlin.annotation.Target(AnnotationTarget.TYPE)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class Supply(val methodName: String)