package com.uxin.middleware.api


import com.uxin.middleware.IComponentsSection
import com.uxin.middleware.KTrack
import com.uxin.middleware.annotation.Rely

@KTrack
interface ILogLibApi : IComponentsSection {

    @Rely(className = "com.uxin.logstaticslib.TestImpl", methodName = "aa")
    fun test(url: String)
}
