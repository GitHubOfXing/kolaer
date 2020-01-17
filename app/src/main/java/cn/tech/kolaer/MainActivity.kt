package cn.tech.kolaer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import cn.tech.kolaer.platform.MainFragment
import cn.tech.kolaer.platform.test.InfoEntity
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.uxin.baselib.*
import com.uxin.component_service.ComponetPlatform
import com.uxin.component_service.Kolaer
import com.uxin.middleware.api.ILogLibApi
import java.util.ArrayList

class MainActivity : BaseActivity() {

    private val resIcon = object : ArrayList<Int>() {
        init {
            add(R.drawable.selector_tab_home_icon)
            add(R.drawable.selector_tab_discovery_icon)
            add(R.drawable.selector_tab_more_icon)
            add(R.drawable.selector_tab_profile_icon)
        }
    }

    private val resTxt = object : ArrayList<Int>() {
        init {
            add(R.string.tab_home)
            add(R.string.tab_finder)
            add(R.string.tab_go)
            add(R.string.tab_center)
        }
    }

    private var mSectionsPagerAdapter: BasePagerAdapter? = null
    private var mViewPager: ViewPager? = null
    private var infoEntities = ArrayList<BaseFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ComponetPlatform.getInstance()
            .registerComponent(ComponetPlatform.ComponentUnit.LOG)

        val kk = Kolaer.Builder()
//            .registClasses(object : HashMap<Class<*>, String>() {
//                init {
//                    put(ILogLibApi::class.java, ComponetPlatform.ComponentUnit.LOG.getValue())
//                }
//            })
            .dynamicRegist(ComponetPlatform.getInstance())
        val loaer = kk.build()

        loaer.create(ILogLibApi::class.java!!).test("lichenxing")
    }

    override fun create() {
        mSectionsPagerAdapter = BasePagerAdapter(supportFragmentManager)

        infoEntities = inflateChildrenPage()
        mSectionsPagerAdapter?.init(infoEntities)

        mViewPager = findViewById<ViewPager>(R.id.container)
        mViewPager?.adapter = mSectionsPagerAdapter

        val tabLayout = findViewById<TabLayout>(R.id.tablayouts)
        tabLayout.setupWithViewPager(mViewPager)


        for (i in infoEntities.indices) {
            tabLayout.getTabAt(i)!!.customView = provideTabView(i)
        }
    }

    override fun provideLayout(): Int {
        return R.layout.activity_main
    }

    private fun inflateChildrenPage(): ArrayList<BaseFragment> {
        val tabFragments = ArrayList<BaseFragment>()
        val gson = Gson()
        for(i in resTxt.indices) {
            val info = InfoEntity(getString(resTxt[i]), i.toString())
            val jsonInfo = gson.toJson(info)
            tabFragments.add(MainFragment.newInstance(jsonInfo,InfoEntity::class.java))
        }
        return tabFragments
    }

    private fun provideTabView(position:Int):View {
        val tab = LayoutInflater.from(this).inflate(R.layout.item_tab, null)
        (tab.findViewById(R.id.tab_img) as ImageView).setImageResource(resIcon[position])
        (tab.findViewById(R.id.tab_txt) as TextView).text = getString(resTxt[position])
        return tab
    }

}
