package com.novel.myapplication.activity

import android.text.TextUtils
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.novel.myapplication.R
import com.novel.myapplication.base.BaseActivity
import com.novel.myapplication.bean.BookBean
import com.novel.myapplication.bean.BoookItemBean
import com.novel.myapplication.dao.BookBeanDao
import com.novel.myapplication.dao.BookItemBeanDao
import com.novel.myapplication.databinding.ActivityPublisHomeBinding
import com.novel.myapplication.utils.SharePrefUtils
import java.text.SimpleDateFormat
import java.util.Date

class PublishActivity : BaseActivity<ActivityPublisHomeBinding>(){

    var type:String =""
    lateinit var bookBeanDao: BookBeanDao
    lateinit var bookItemBeanDao: BookItemBeanDao

    var dataList: MutableList<BoookItemBean> = mutableListOf()
    var  count =1


    override fun onResume() {
        super.onResume()
        initView()
    }
    protected fun initView() {
        bookBeanDao = BookBeanDao()
        bookItemBeanDao = BookItemBeanDao()
        mViewBinding.radioGroupUnassured.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> //获取被选中的radiobutton的id
            val rcheck = findViewById<View>(checkedId) as RadioButton
            //获取
            val checkText = rcheck.text.toString()
            if (checkText == getString(R.string.publish_type_kh)) {

            } else if (checkText == getString(R.string.publish_type_qh)) {

            } else if (checkText == getString(R.string.publish_type_xy)) {

            }
            else if (checkText == getString(R.string.publish_type_lm)) {

            }
            else if (checkText == getString(R.string.publish_type_other)) {
                //其他

            };
            type = checkText
            Toast.makeText(this@PublishActivity, "您选中的是：$checkText", Toast.LENGTH_SHORT)
                .show()
        })

        mViewBinding.btnRegister.setOnClickListener(View.OnClickListener {
            val bookBean = BookBean()
            bookBean.id=System.currentTimeMillis().toString() + ""
            bookBean.bookName=mViewBinding.editTitle.getText().toString()
            bookBean.author =SharePrefUtils.getName(this@PublishActivity)
            bookBean.picture =""
            bookBean.book_type =type
            bookBean.time= getDate()
            //添加书籍信息
            bookBeanDao.addOrUpdate(bookBean)
            //添加故事文章
            if (count<=dataList.size){
                val itemBean = BoookItemBean()
                itemBean.chapter = mViewBinding.chapter.text.toString()
                itemBean.content = mViewBinding.chapterContentEdw.text.toString()
                itemBean.author = SharePrefUtils.getName(this@PublishActivity)
                itemBean.bookName = mViewBinding.editTitle.getText().toString()
                itemBean.book_type = type
                itemBean.id = System.currentTimeMillis().toString() + ""
                itemBean.page = 1.toString()
                dataList.add(itemBean)
            }
            if(dataList!= null && dataList.size>0){

                for (data in dataList){
                    bookItemBeanDao.addOrUpdate(data)
                }

            }else {
                val itemBean = BoookItemBean()
                itemBean.chapter = mViewBinding.chapter.text.toString()
                itemBean.content = mViewBinding.chapterContentEdw.text.toString()
                itemBean.author = SharePrefUtils.getName(this@PublishActivity)
                itemBean.bookName = mViewBinding.editTitle.getText().toString()
                itemBean.book_type = type
                itemBean.id = System.currentTimeMillis().toString() + ""
                itemBean.page = 1.toString()
                bookItemBeanDao.addOrUpdate(itemBean)
            }



            Toast.makeText(this@PublishActivity, "添加成功！", Toast.LENGTH_LONG).show()
            finish()
        })

        mViewBinding.btnNextChapter.setOnClickListener {

            if (TextUtils.isEmpty(mViewBinding.chapterContentEdw.text.toString())){
                Toast.makeText(this@PublishActivity, getString(R.string.tips_c), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            count ++
            mViewBinding.count.setText("第"+count+"页")

            val itemBean = BoookItemBean()
            itemBean.chapter = mViewBinding.chapter.text.toString()
            itemBean.content = mViewBinding.chapterContentEdw.text.toString()
            itemBean.author =SharePrefUtils.getName(this@PublishActivity)
            itemBean.bookName =mViewBinding.editTitle.getText().toString()
            itemBean.book_type =type
            itemBean.id = System.currentTimeMillis().toString()+""
            itemBean.page = count.toString()

            dataList.add(itemBean) //缓存list



        }
    }

    fun getDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date(System.currentTimeMillis())
        println(formatter.format(date))
        return formatter.format(date)
    }

}