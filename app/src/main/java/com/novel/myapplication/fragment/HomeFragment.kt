package com.novel.myapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.novel.myapplication.activity.PublishActivity
import com.novel.myapplication.adapter.BookListAdapter
import com.novel.myapplication.bean.BookBean
import com.novel.myapplication.dao.BookBeanDao
import com.novel.myapplication.dao.BookItemBeanDao
import com.novel.myapplication.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Date

class HomeFragment : Fragment(){
    private var fragmentHomeBinding: FragmentHomeBinding? = null
    var mRecyclerAdapter: BookListAdapter? = null
    var dataList: MutableList<BookBean> = mutableListOf()
    var bookItemBeanDao:BookItemBeanDao?=null
    var bookBeanDao: BookBeanDao?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding = inflater?.let { FragmentHomeBinding.inflate(it, container, false) }
        return fragmentHomeBinding!!!!.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }


    override fun onResume() {
        super.onResume()
        bookItemBeanDao = BookItemBeanDao()
        bookBeanDao = BookBeanDao()
        fragmentHomeBinding!!.publish.setOnClickListener(){
            var intent = Intent(activity, PublishActivity::class.java)
            activity?.startActivity(intent)
        }
        dataList  = bookBeanDao!!.getContactAll2() as MutableList<BookBean>;
        mRecyclerAdapter = context?.let { BookListAdapter(it,dataList,) }
        fragmentHomeBinding!!.recycler.setLayoutManager(LinearLayoutManager(activity))
        fragmentHomeBinding!!.recycler.setAdapter(mRecyclerAdapter)

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