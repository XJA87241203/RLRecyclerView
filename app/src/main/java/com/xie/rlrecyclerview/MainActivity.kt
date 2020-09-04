package com.xie.rlrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xie.librlrecyclerview.other.OnLoadMoreListener
import com.xie.librlrecyclerview.other.OnRefreshListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var adapter: MyAdapter
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rl_rv.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        adapter = MyAdapter()
        rl_rv.adapter = adapter
        rl_rv.refreshEnable = true
        rl_rv.onRefreshListener = object : OnRefreshListener {
            override fun onRefresh() {
                Handler().postDelayed({
                    page = 1
                    adapter.setListData(getListData(page, 30))
                    adapter.notifyDataSetChanged()
                    rl_rv.finishRefresh()
                }, 2000)
            }
        }
        rl_rv.autoLoadMoreEnable = true
        rl_rv.onLoadMoreListener = object : OnLoadMoreListener {
            override fun onLoadMore() {
                Handler().postDelayed({
                    page++
                    adapter.addListData(getListData(page, 30))
                    adapter.notifyDataSetChanged()
                    rl_rv.finishLoadMore()
                }, 2000)
            }
        }
        rl_rv.startRefresh()
    }

    fun getListData(page: Int, size: Int): ArrayList<String> {
        val start = ((page - 1) * size) + 1
        val result = ArrayList<String>()
        for (i in start..size * page) {
            result.add("第${i}个")
        }
        return result
    }
}