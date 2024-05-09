package com.novel.myapplication.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.novel.myapplication.R
import com.novel.myapplication.adapter.BookCollListAdapter
import com.novel.myapplication.base.BaseActivity
import com.novel.myapplication.bean.BookBean
import com.novel.myapplication.dao.BookBeanDao
import com.novel.myapplication.dao.BookItemBeanDao
import com.novel.myapplication.databinding.ActivityCollBinding
import com.novel.myapplication.utils.SharePrefUtils

class BookCollsActivity : BaseActivity<ActivityCollBinding>(){

    var type:String =""
    lateinit var bookBeanDao: BookBeanDao
    lateinit var bookItemBeanDao: BookItemBeanDao

    var dataList: MutableList<BookBean> = mutableListOf()
    var  count =1
    var mRecyclerAdapter : BookCollListAdapter?= null


    override fun onResume() {
        super.onResume()
        initView()
       var bookId= intent.getStringExtra("id")
        dataList = bookBeanDao.bookBeanList(SharePrefUtils.getName(this@BookCollsActivity)) as MutableList<BookBean>

        mRecyclerAdapter = BookCollListAdapter(this@BookCollsActivity,dataList)
        mViewBinding!!.recycler.setLayoutManager(LinearLayoutManager(this@BookCollsActivity))
        mViewBinding!!.recycler.setAdapter(mRecyclerAdapter)


//        if(dataList!= null &&dataList.size>0) {
//            mViewBinding.chapterContentEdw.setText(dataList.get(0).content)
//            mViewBinding.chapter.setText(dataList.get(0).chapter)
//            if (!TextUtils.isEmpty(dataList.get(0).picture)) {
//                val bitmap = BitmapFactory.decodeFile(dataList.get(0).picture)
//                mViewBinding.img.setImageBitmap(bitmap)
//            }
//        }
        mViewBinding.tl.titleView.text =getString(R.string.my_coll)
        mViewBinding.tl.btnBack.setOnClickListener({
            finish()
        })
    }
    protected fun initView() {
        bookBeanDao = BookBeanDao()
        bookItemBeanDao = BookItemBeanDao()

    }
}