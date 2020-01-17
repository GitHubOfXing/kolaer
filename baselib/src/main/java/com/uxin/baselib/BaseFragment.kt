package com.uxin.baselib

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.IntegerRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

import com.google.gson.Gson
import kotlin.properties.ReadOnlyProperty

/**
 * lichenxing
 */
abstract class BaseFragment : Fragment() {

    private var mContext: Context? = null
    private var isFirstLoad: Boolean = false // 是否第一次加载

    private var mProgressDialog: ProgressDialog? = null // 加载进度对话框

    private var savedState: Bundle? = null

    val title: CharSequence?
        get() = if (context != null) {
            context!!.resources.getString(fragmentPageName())
        } else null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

        val bundle = arguments
        if (bundle != null && bundle.size() > 0) {
            initVariables(bundle)
        }
        isFirstLoad = true
    }

    @LayoutRes
    abstract fun getContentViewId(): Int

    open fun initVariables(bundle: Bundle) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(mContext).inflate(getContentViewId(), null)
        initViews(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            initData()
            initEvent()
            isFirstLoad = false
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            restoreStateFromArguments()
        }
    }

    private fun restoreStateFromArguments(): Boolean {
        val b = arguments
        if (b != null) {
            savedState = b.getBundle("internalSavedViewState8954201239547")
            if (savedState != null) {
                return true
            }
        }
        return false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save State Here
        saveStateToArguments()
    }

    private fun saveStateToArguments() {
        if (view != null)
            savedState = saveState()
        if (savedState != null && arguments != null) {
            val b = arguments
            b!!.putBundle("internalSavedViewState8954201239547", savedState)
        }
    }

    private fun saveState(): Bundle {
        return Bundle()
    }

    /**
     * 初始化视图
     *
     * @param view
     */
    protected abstract fun initViews(view: View)

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     * 初始化事件
     */
    protected abstract fun initEvent()

    /**
     * 显示提示框
     *
     * @param msg 等待消息字符串
     */
    protected fun showProgressDialog(msg: String) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(mContext)
        }
        if (mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
        mProgressDialog!!.setMessage(msg)
        mProgressDialog!!.show()
    }

    /**
     * 隐藏提示框
     */
    protected fun hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }

    @StringRes
    protected abstract fun fragmentPageName(): Int

    companion object {

        const val ARG_INFO_ENTITY = "arg_info_entity"

        fun bridgeMsg(jsonInfo: String?, clazz: Class<*>?): Bundle? {
            var bundle: Bundle? = null
            if (!TextUtils.isEmpty(jsonInfo) && clazz != null) {
                val gson = Gson()
                if (clazz.isAssignableFrom(Parcelable::class.java)) {
                    bundle = Bundle()
                    val target = gson.fromJson(jsonInfo, clazz)
                    bundle.putParcelable(ARG_INFO_ENTITY, target as Parcelable)
                }
            }
            return bundle
        }
    }
}
