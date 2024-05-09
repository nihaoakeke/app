package com.novel.myapplication.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
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
    var absolutePath_gs =""
    var absolutePath =""

    var bookBean = BookBean()

    override fun onResume() {
        super.onResume()
        initView()
        mViewBinding.tl.btnBack.setOnClickListener({
            finish()
        })
    }
    protected fun initView() {
        bookBeanDao = BookBeanDao()
        bookItemBeanDao = BookItemBeanDao()


        var bookId= intent.getStringExtra("id")
        bookId?.let {

            bookBeanDao.bookBean(bookId)?.let {
                bookBean =it
            }
            dataList = bookItemBeanDao.getBookItem(bookId) as MutableList<BoookItemBean>

            if (bookBean!= null){
                mViewBinding.editTitle.setText(bookBean.bookName)
                var textType= bookBean.book_type
                if (textType == getString(R.string.publish_type_kh)) {
                    mViewBinding.kn.isChecked = true
                } else if (textType == getString(R.string.publish_type_qh)) {
                    mViewBinding.qh.isChecked = true
                } else if (textType == getString(R.string.publish_type_xy)) {
                    mViewBinding.yb.isChecked = true
                }
                else if (textType == getString(R.string.publish_type_lm)) {
                    mViewBinding.lm.isChecked = true
                }
                else if (textType == getString(R.string.publish_type_other)) {
                    //其他
                    mViewBinding.other.isChecked = true
                };

                if (!TextUtils.isEmpty(bookBean.picture)) {
                    val bitmap = BitmapFactory.decodeFile(bookBean.picture)
                    mViewBinding.fm.setImageBitmap(bitmap)
                }

                if(dataList!= null &&dataList.size>0) {
                    mViewBinding.chapterContentEdw.setText(dataList.get(0).content)
                    mViewBinding.chapter.setText(dataList.get(0).chapter)
                    if (!TextUtils.isEmpty(dataList.get(0).picture)) {
                        val bitmap = BitmapFactory.decodeFile(dataList.get(0).picture)
                        mViewBinding.img.setImageBitmap(bitmap)
                    }
                }
            }


        }
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
            bookBean.picture =absolutePath
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
                itemBean.picture =absolutePath_gs
                itemBean.id = System.currentTimeMillis().toString() + ""
                itemBean.page = 1.toString()
                itemBean.last = 0
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
                itemBean.picture =absolutePath_gs
                itemBean.id = System.currentTimeMillis().toString() + ""
                itemBean.page = 1.toString()
                itemBean.last = 0
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
            itemBean.picture =absolutePath_gs
            itemBean.id = System.currentTimeMillis().toString()+""
            itemBean.page = count.toString()
            itemBean.last = 0

            dataList.add(itemBean) //缓存list



        }

        mViewBinding.fm.setOnClickListener({
            openImagePicker()
        })
        mViewBinding.img.setOnClickListener({
            openImagePickerImg()
        })
    }

    fun getDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date(System.currentTimeMillis())
        println(formatter.format(date))
        return formatter.format(date)
    }


    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }
    private fun openImagePickerImg() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE_GS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var contentResolver = contentResolver
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
             absolutePath = getRealPathFromURI(this@PublishActivity, imageUri!!).toString()
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            mViewBinding.fm.setImageBitmap(bitmap)
        }
        else if (requestCode == PICK_IMAGE_REQUEST_CODE_GS && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
             absolutePath_gs = let { imageUri?.let { it1 -> getRealPathFromURI(it, it1).toString()}.toString() }
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            mViewBinding.img.setImageBitmap(bitmap)
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST_CODE = 100
        private const val PICK_IMAGE_REQUEST_CODE_GS = 101
    }

    fun getRealPathFromURI(context: Context, uri: Uri): String? {
        var result: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.let {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                result = it.getString(columnIndex)
            }
            it.close()
        }
        return result
    }



}