package com.novel.myapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.novel.myapplication.R
import com.novel.myapplication.R.mipmap.xuexiao
import com.novel.myapplication.activity.PublishActivity
import com.novel.myapplication.adapter.BookListAdapter
import com.novel.myapplication.adapter.HomeItemAdapter
import com.novel.myapplication.bean.BookBean
import com.novel.myapplication.bean.HomeItem
import com.novel.myapplication.dao.BookBeanDao
import com.novel.myapplication.dao.BookItemBeanDao
import com.novel.myapplication.databinding.FragmentHomeBinding
import com.novel.myapplication.utils.SharePrefUtils
import java.text.SimpleDateFormat
import java.util.Date

class HomeFragment : Fragment(){
    private var fragmentHomeBinding: FragmentHomeBinding? = null
    var mRecyclerAdapter: HomeItemAdapter? = null
    var dataList: MutableList<HomeItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding = inflater?.let { FragmentHomeBinding.inflate(it, container, false) }
        return fragmentHomeBinding!!!!.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var homeIte = HomeItem()
        homeIte.name = "学校信息"
        var homeIte1 = HomeItem()
        homeIte1.name = "最新消息"
        var homeIte2 = HomeItem()
        homeIte2.name = "抽样调查"
        var homeIte3 = HomeItem()
        homeIte3.name = "学生菜谱"
        var homeIte4 = HomeItem()
        homeIte4.name = "营养搭配"
        var homeIte5 = HomeItem()
        homeIte5.name ="卫生检查"
        var homeIte6 = HomeItem()
        homeIte6.name="投诉统计"
        dataList.add(homeIte)
        dataList.add(homeIte1)
        dataList.add(homeIte2)
        dataList.add(homeIte3)
        dataList.add(homeIte4)
        dataList.add(homeIte5)
        dataList.add(homeIte6)

    }


    override fun onDestroyView() {
        super.onDestroyView()
    }


    override fun onResume() {
        super.onResume()
        if (SharePrefUtils.getLoginType(requireContext()).equals(requireActivity().getString(R.string.user_type_reader))){
            fragmentHomeBinding!!.publish.visibility =View.GONE
        }
        fragmentHomeBinding!!.publish.setOnClickListener(){
            var intent = Intent(activity, PublishActivity::class.java)
            activity?.startActivity(intent)
        }



        mRecyclerAdapter = context?.let { HomeItemAdapter(it,dataList) }
        fragmentHomeBinding!!.recycler.setLayoutManager(GridLayoutManager(activity,4))
        fragmentHomeBinding!!.recycler.setAdapter(mRecyclerAdapter)


        fragmentHomeBinding!!.input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 在文本变化之前执行的操作
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 在文本变化时执行的操作
            }

            override fun afterTextChanged(s: Editable?) {
                // 在文本变化之后执行的操作
                val newText = s.toString().trim()
//                filterData(newText)
            }
        })
        fragmentHomeBinding!!.kh.setOnClickListener(){

        }
    }


    fun splitTabLayout(tabLayout: TabLayout) {
        if (tabLayout.tabCount < 2) {
            return
        }
        tabLayout.visibility = View.INVISIBLE
        if (tabLayout.tabMode != TabLayout.MODE_FIXED || tabLayout.tabGravity != TabLayout.GRAVITY_FILL) {
            return
        }
        tabLayout.post(Runnable {
            val tabCount = tabLayout.tabCount
            val measuredWidth = tabLayout.measuredWidth
            if (measuredWidth <= 0) {
                tabLayout.visibility = View.VISIBLE
                return@Runnable
            }
            val marginOffset = measuredWidth / tabCount / 4
            val layoutParams = tabLayout.layoutParams
            if (layoutParams is MarginLayoutParams) {
                val marginLayoutParams = layoutParams
                marginLayoutParams.leftMargin = marginOffset
                marginLayoutParams.rightMargin = marginOffset
                tabLayout.layoutParams = layoutParams
                //Log.debug(TAG, "splitTabLayout: " + marginOffset);
            }
            tabLayout.visibility = View.VISIBLE
        })
    }

    fun getDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date(System.currentTimeMillis())
        println(formatter.format(date))
        return formatter.format(date)
    }


}