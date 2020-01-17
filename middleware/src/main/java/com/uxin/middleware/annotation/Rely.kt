package com.uxin.middleware.annotation


@MustBeDocumented
@kotlin.annotation.Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class Rely(val className: String, val methodName: String)