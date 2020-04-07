package cn.tech.kolaer.platform.page

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.tech.kolaer.R
import cn.tech.kolaer.platform.BasePresenterSelector
import cn.tech.kolaer.platform.presenter.OnePresenter
import cn.tech.kolaer.platform.presenter.TwoPresenter
import cn.tech.kolaer.platform.test.InfoEntity
import cn.tech.kolaer.platform.test.OneBean
import cn.tech.kolaer.platform.test.TwoBean
import com.uxin.baselib.BaseFragment
import com.uxin.baselib.list.ArrayObjectDataAdapter
import com.uxin.baselib.list.BridgeAdapter
import kotlinx.coroutines.*
import java.util.ArrayList


class MainFragment : BaseFragment() {

    private var info: InfoEntity? = null
    private var mRecyclerView: RecyclerView? = null
    private var adapter:ArrayObjectDataAdapter? = null

    override fun initVariables(bundle: Bundle) {
        info = bundle.getParcelable(ARG_INFO_ENTITY)
    }

    override fun initViews(view: View) {
        mRecyclerView = view.findViewById<RecyclerView>(R.id.page_list)
        mRecyclerView!!.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        var selector = BasePresenterSelector()
        val one = OnePresenter()
        val two = TwoPresenter()
        selector.addPresenter(OneBean::class.java, one)
        selector.addPresenter(TwoBean::class.java, two)

        adapter = ArrayObjectDataAdapter(selector)

        val bridge = BridgeAdapter(adapter,selector)
        mRecyclerView!!.adapter = bridge
    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_tab_main
    }

    override fun initData() {
        val list = ArrayList<Any>()
        for (i in 0..100) {
            if (i % 2 == 0) {
                val o1 = OneBean()
                o1.value = i
                list.add(o1)
            } else {
                val t2 = TwoBean()
                t2.value = i
                list.add(t2)
            }
        }
        adapter?.addAll(list)
    }

    override fun initEvent() {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("TAG", "MainFragment onAttach()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "MainFragment onCreate()")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("TAG", "MainFragment onActivityCreated()")
    }

    override fun onStart() {
        super.onStart()
        Log.e("TAG", "MainFragment onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.e("TAG", "MainFragment onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.e("TAG", "MainFragment onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.e("TAG", "MainFragment onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("TAG", "MainFragment onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TAG", "MainFragment onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("TAG", "MainFragment onDetach()")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.e("TAG", "MainFragment isVisibleToUser = $isVisibleToUser")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.e("TAG", "MainFragment onHiddenChanged = $hidden")
    }

    companion object {

        fun newInstance(jsonInfo: String?,clazz:Class<*>?): MainFragment {
            val fragment = MainFragment()
            if(!TextUtils.isEmpty(jsonInfo) && clazz != null) {
                val args = bridgeMsg(jsonInfo,clazz)
                fragment.arguments = args
            }
            return fragment
        }
    }

    @StringRes
    override fun fragmentPageName(): Int {
        return R.string.tab_home
    }
}
