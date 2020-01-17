package com.uxin.baselib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import android.view.ViewStub

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.uxin.baselib.util.StatusBarUtil

/**
 * lichenxing
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        val mChildContainer = findViewById<ViewStub>(R.id.fl_container)
        mChildContainer.layoutResource = provideLayout()
        mChildContainer.inflate()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        create()
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        StatusBarUtil.setColor(this, resources.getColor(R.color.title_color))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.container_menu, menu)
        return true
    }

    /**
     * 提供布局
     */
    protected abstract fun provideLayout(): Int

    protected abstract fun create()

}
