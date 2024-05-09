package com.novel.myapplication.activity

import android.graphics.BitmapFactory
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.novel.myapplication.base.BaseActivity
import com.novel.myapplication.bean.BookBean
import com.novel.myapplication.bean.BoookItemBean
import com.novel.myapplication.dao.BookBeanDao
import com.novel.myapplication.dao.BookItemBeanDao
import com.novel.myapplication.databinding.ActivityDetailsBinding
import com.novel.myapplication.utils.SharePrefUtils

class BookDetailsActivity : BaseActivity<ActivityDetailsBinding>(){

    var type:String =""
    lateinit var bookBeanDao: BookBeanDao
    lateinit var bookItemBeanDao: BookItemBeanDao

    var bookBean = BookBean()

    var dataList: MutableList<BoookItemBean> = mutableListOf()
    var  count =1


    override fun onResume() {
        super.onResume()
        initView()
       var bookId= intent.getStringExtra("id")

        bookBeanDao.bookBean(bookId)?.let {
            bookBean =it
        }
        dataList = bookItemBeanDao.getBookItem(bookId) as MutableList<BoookItemBean>

        mViewBinding.editTitle.text = bookId


        if(dataList!= null &&dataList.size>0) {
            if (bookBean!= null ){
                mViewBinding.chapterContentEdw.setText(bookBean!!.last?.let { dataList.get(it).content })
                mViewBinding.chapter.setText(bookBean.last?.let { dataList.get(it).chapter })
                if (!TextUtils.isEmpty(dataList.get(bookBean.last!!).picture)) {
                    val bitmap = BitmapFactory.decodeFile(dataList.get(bookBean.last!!).picture)
                    mViewBinding.img.setImageBitmap(bitmap)
                }
            }
            else {
                mViewBinding.chapterContentEdw.setText(dataList.get(0).content)
                mViewBinding.chapter.setText(dataList.get(0).chapter)
                if (!TextUtils.isEmpty(dataList.get(0).picture)) {
                    val bitmap = BitmapFactory.decodeFile(dataList.get(0).picture)
                    mViewBinding.img.setImageBitmap(bitmap)
                }
            }

        }
        mViewBinding.tl.btnBack.setOnClickListener {
            finish()
        }
    }
    protected fun initView() {
        bookBeanDao = BookBeanDao()
        bookItemBeanDao = BookItemBeanDao()

        mViewBinding.btnRegister.setOnClickListener(View.OnClickListener {
            val bookBean = BookBean()
            bookBean.id=System.currentTimeMillis().toString() + ""
            bookBean.bookName=mViewBinding.editTitle.getText().toString()
            bookBean.author =SharePrefUtils.getName(this@BookDetailsActivity)
            bookBean.picture =""
            bookBean.book_type =type
            bookBean.time= ""
            //添加书籍信息
            bookBeanDao.addOrUpdate(bookBean)
            //添加故事文章
            if(dataList!= null && dataList.size>0){

                for (data in dataList){
                    bookItemBeanDao.addOrUpdate(data)
                }

            }else {
                val itemBean = BoookItemBean()
                itemBean.chapter = mViewBinding.chapter.text.toString()
                itemBean.content = mViewBinding.chapterContentEdw.text.toString()
                itemBean.author = SharePrefUtils.getName(this@BookDetailsActivity)
                itemBean.bookName = mViewBinding.editTitle.getText().toString()
                itemBean.book_type = type
                itemBean.id = System.currentTimeMillis().toString() + ""
                itemBean.page = 1.toString()
                bookItemBeanDao.addOrUpdate(itemBean)
            }



            Toast.makeText(this@BookDetailsActivity, "添加成功！", Toast.LENGTH_LONG).show()
            finish()
        })

        mViewBinding.btnNextChapter.setOnClickListener {
           if (dataList.size<=count){
               Toast.makeText(this@BookDetailsActivity,"没有更多了",Toast.LENGTH_LONG).show()
               return@setOnClickListener
           }
            count ++
            mViewBinding.count.setText("第"+count+"页")
            dataList.get(count-1).count = dataList.get(count-1).count?.plus(1);
            dataList.get(count-1).last = count-1;
            bookItemBeanDao.addOrUpdate(dataList.get(count-1))
            bookBean.count = bookBean.count?.plus(1);
            bookBeanDao.addOrUpdate(bookBean)
            if (!TextUtils.isEmpty(dataList.get(count-1).picture)) {
                val bitmap = BitmapFactory.decodeFile(dataList.get(count-1).picture)
                mViewBinding.img.setImageBitmap(bitmap)
            }
           mViewBinding.chapterContentEdw.text= dataList.get(count-1).content



        }
    }
}