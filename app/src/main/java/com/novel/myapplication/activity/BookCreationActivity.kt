package com.novel.myapplication.activity

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.novel.myapplication.R
import com.novel.myapplication.adapter.BookCollListAdapter
import com.novel.myapplication.adapter.BookCreateListAdapter
import com.novel.myapplication.base.BaseActivity
import com.novel.myapplication.bean.BookBean
import com.novel.myapplication.dao.BookBeanDao
import com.novel.myapplication.dao.BookItemBeanDao
import com.novel.myapplication.databinding.ActivityCollBinding
import com.novel.myapplication.utils.SharePrefUtils

class BookCreationActivity : BaseActivity<ActivityCollBinding>(){

    var type:String =""
    lateinit var bookBeanDao: BookBeanDao
    lateinit var bookItemBeanDao: BookItemBeanDao

    var dataList: MutableList<BookBean> = mutableListOf()
    var  count =1
    var mRecyclerAdapter : BookCreateListAdapter?= null


    override fun onResume() {
        super.onResume()
        initView()
        dataList = bookBeanDao.getBookBeanList(SharePrefUtils.getName(this@BookCreationActivity)) as MutableList<BookBean>



        mRecyclerAdapter = BookCreateListAdapter(this@BookCreationActivity,dataList)
        mViewBinding!!.recycler.setLayoutManager(GridLayoutManager(this@BookCreationActivity,3))
        mViewBinding!!.recycler.setAdapter(mRecyclerAdapter)


//        if(dataList!= null &&dataList.size>0) {
//            mViewBinding.chapterContentEdw.setText(dataList.get(0).content)
//            mViewBinding.chapter.setText(dataList.get(0).chapter)
//            if (!TextUtils.isEmpty(dataList.get(0).picture)) {
//                val bitmap = BitmapFactory.decodeFile(dataList.get(0).picture)
//                mViewBinding.img.setImageBitmap(bitmap)
//            }
//        }
        mViewBinding.tl.titleView.text=getString(R.string.my_crate)
        mViewBinding.tl.btnBack.setOnClickListener({
            finish()
        })
    }
    protected fun initView() {
        bookBeanDao = BookBeanDao()
        bookItemBeanDao = BookItemBeanDao()

    }
}