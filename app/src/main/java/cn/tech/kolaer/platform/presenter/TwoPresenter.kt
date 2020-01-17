package cn.tech.kolaer.platform.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.uxin.baselib.list.ViewPresenter

import cn.tech.kolaer.R
import cn.tech.kolaer.platform.test.TwoBean

class TwoPresenter : ViewPresenter() {

    override fun onCreateViewHolder(p0: ViewGroup): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.two, p0, false)
        return TwoHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Any) {
        (p0 as TwoHolder).textView.text = (p1 as TwoBean).value.toString()
    }

    override fun onUnbindViewHolder(p0: ViewHolder) {

    }


    internal inner class TwoHolder(view: View) : ViewHolder(view) {

        var textView: TextView = view.findViewById(R.id.tv_num)

    }
}
