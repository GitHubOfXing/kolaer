package cn.tech.kolaer.platform

import com.uxin.baselib.list.ViewPresenter
import com.uxin.baselib.list.ViewPresenterSelector
import java.util.ArrayList
import java.util.HashMap

class BasePresenterSelector : ViewPresenterSelector() {

    private val mClassMap = HashMap<Class<*>, ViewPresenter>()
    private val mPresenters = ArrayList<ViewPresenter>()

    fun addPresenter(type:Class<*>, presenter: ViewPresenter) {

        mClassMap[type] = presenter
        if (!mPresenters.contains(presenter)) {
            mPresenters.add(presenter)
        }
    }

    override fun getPresenter(item: Any): ViewPresenter? {
        return mClassMap[item.javaClass]
    }

    override fun getPresenters(): Array<ViewPresenter> {
        return mPresenters.toTypedArray()
    }

}