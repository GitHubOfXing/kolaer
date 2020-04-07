package cn.tech.kolaer.platform.page

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import cn.tech.kolaer.R
import cn.tech.kolaer.platform.page.layer.ErrorCover
import cn.tech.kolaer.platform.page.layer.LoadingCover
import cn.tech.kolaer.platform.test.InfoEntity
import cn.tech.kolaer.platform.test.NetworkEventProducer
import cn.tech.kolaer.platform.util.RouterKeyInter
import com.uxin.baselib.BaseFragment
import com.uxin.baselib.layer.EventLayerLayout
import com.uxin.baselib.layer.event.EventKey
import com.uxin.baselib.layer.event.producer.BaseEventProducer
import com.uxin.baselib.layer.event.resumer.ResumerGroupAssembly
import com.uxin.baselib.layer.helper.OnRouterHelperListener


class LayerFragment : BaseFragment() {

    private lateinit var groupAssembly: ResumerGroupAssembly
    private var info: InfoEntity? = null
    private val groupRouterHelper: OnRouterHelperListener = object: OnRouterHelperListener {

        override fun onResumerEventByNative(eventKey: EventKey.NativeKey, jsonEvent: String) {
            Log.e("chenxing","LayerFragment----ResumerNativeEvent--$jsonEvent")
        }

        override fun onResumerEventByExpand(eventKey: EventKey.ExpandKey, jsonEvent: String) {
            Log.e("chenxing","LayerFragment----onResumerEventByExpand--$jsonEvent")
        }

    }

    override fun initVariables(bundle: Bundle) {
        info = bundle.getParcelable(ARG_INFO_ENTITY)
    }

    override fun initViews(view: View) {
        val layerLayout = view.findViewById<EventLayerLayout>(R.id.layerLayout)
        if(context != null) {
            groupAssembly = ResumerGroupAssembly()
            groupAssembly.bindGroupRouterHelper(groupRouterHelper)
            groupAssembly.addEventResumer(RouterKeyInter.KEY_LOADING_COVER, LoadingCover(context!!))
            groupAssembly.addEventResumer(RouterKeyInter.KEY_ERROR_COVER,ErrorCover(context!!))
            var expandMap = HashMap<String, BaseEventProducer>()
            expandMap[RouterKeyInter.KEY_NETWORK_SOURCE] = NetworkEventProducer(context!!)
            layerLayout.initExpandEventSource(expandMap)

            layerLayout.initEventSource(groupAssembly)
        }
    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_tab_finder
    }

    override fun initData() {

    }

    override fun initEvent() {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("TAG", "LayerFragment onAttach()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "LayerFragment onCreate()")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("TAG", "LayerFragment onActivityCreated()")
    }

    override fun onStart() {
        super.onStart()
        Log.e("TAG", "LayerFragment onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.e("TAG", "LayerFragment onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.e("TAG", "LayerFragment onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.e("TAG", "LayerFragment onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("TAG", "LayerFragment onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TAG", "LayerFragment onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("TAG", "LayerFragment onDetach()")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.e("TAG", "LayerFragment isVisibleToUser = $isVisibleToUser")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.e("TAG", "LayerFragment onHiddenChanged = $hidden")
    }

    companion object {

        fun newInstance(jsonInfo: String?,clazz:Class<*>?): LayerFragment {
            val fragment = LayerFragment()
            if(!TextUtils.isEmpty(jsonInfo) && clazz != null) {
                val args = bridgeMsg(jsonInfo,clazz)
                fragment.arguments = args
            }
            return fragment
        }
    }

    @StringRes
    override fun fragmentPageName(): Int {
        return R.string.tab_finder
    }
}
