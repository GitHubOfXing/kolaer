package cn.tech.kolaer.platform.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.uxin.baselib.list.ViewPresenter

import cn.tech.kolaer.R
import cn.tech.kolaer.platform.test.OneBean

class OnePresenter : ViewPresenter() {

    override fun onCreateViewHolder(p0: ViewGroup): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.one, p0, false)
        return OneHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Any) {
        (p0 as OneHolder).textView.text = (p1 as OneBean).value.toString()
    }

    override fun onUnbindViewHolder(p0: ViewHolder) {

    }

    internal inner class OneHolder(view: View) : ViewHolder(view) {
        var textView: TextView = view.findViewById(R.id.tv_num)
    }
}
