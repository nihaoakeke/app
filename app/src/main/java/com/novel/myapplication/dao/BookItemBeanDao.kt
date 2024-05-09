package com.novel.myapplication.dao

import com.novel.myapplication.bean.BoookItemBean
import com.novel.myapplication.db.DatabaseOpenHelper
import org.xutils.DbManager
import org.xutils.common.util.KeyValue
import org.xutils.db.sqlite.WhereBuilder
import org.xutils.ex.DbException
import java.io.IOException

class BookItemBeanDao {


    private val TAG = BookItemBeanDao::class.java.simpleName
    private var dbManager: DbManager? = null
    private var isSuccess = false
    private var contactList: List<BoookItemBean>? = null
    constructor()  {
        dbManager = DatabaseOpenHelper.getInstance()
    }

    //保存或者更新联系人数据
    fun addOrUpdate(bookItemBean: BoookItemBean) {
        try {
            if (dbManager== null){
                dbManager = DatabaseOpenHelper.getInstance()
            }
            val itemBean: BoookItemBean? = dbManager?.findById(BoookItemBean::class.java, bookItemBean.id)
            if (itemBean == null) {
                dbManager!!.save(bookItemBean)
            } else {
                val builder = WhereBuilder.b()
                builder.and("id", "=", bookItemBean.id)
                val key1 = KeyValue("bookName", bookItemBean.bookName)
                val key2 = KeyValue("chapter", bookItemBean.chapter)
                val key3 = KeyValue("content", bookItemBean.content)
                val key4 = KeyValue("author", bookItemBean.author)
                val key5 = KeyValue("book_type", bookItemBean.book_type)
                val key6 = KeyValue("page", bookItemBean.page)
                val key7 = KeyValue("time", bookItemBean.time)
                val key8 = KeyValue("picture", bookItemBean.picture)
                //                builder.and("id", "=", ContextBean.getId());
//
                dbManager!!.update(bookItemBean::class.java, builder, key1, key2, key3,key4,key5,key6,key7,key8)
                //                dbManager.update(contact);
            }
        } catch (e: DbException) {
            e.printStackTrace()
        }
    }

    //根据条件查找数据
    fun getContactAll2(): List<BoookItemBean>? {
        try {

//            String user_id = LoginManager.getUserId();
            contactList = dbManager!!.selector(BoookItemBean::class.java).findAll()
        } catch (e: DbException) {
            e.printStackTrace()
        }
        return contactList
    }

    //查找全部数据
    fun getContactAll(): List<BoookItemBean>? {
        try {
            contactList = dbManager!!.selector(BoookItemBean::class.java).findAll()
        } catch (e: DbException) {
            e.printStackTrace()
        }
        return contactList
    }

    fun getUser(name: String?): BoookItemBean? {
        var bookItemBean: BoookItemBean? = null
        try {
            val builder = WhereBuilder.b()
            builder.and("name", "=", name)
            bookItemBean = dbManager!!.selector(BoookItemBean::class.java).where(
                "name",
                "=", name
            ).findFirst()
        } catch (e: DbException) {
            e.printStackTrace()
        }
        return bookItemBean
    }
    fun getBookItem(name: String?): List<BoookItemBean?> {
        var bookItemBean: List<BoookItemBean?> = mutableListOf()
        try {
            val builder = WhereBuilder.b()
            builder.and("name", "=", name)
            bookItemBean = dbManager!!.selector(BoookItemBean::class.java).where(
                "book_name",
                "=", name
            ).findAll()
        } catch (e: DbException) {
            e.printStackTrace()
        }
        return bookItemBean
    }

    fun delete(itemBean: BoookItemBean?): Boolean {
        try {
            dbManager!!.delete(itemBean)
            isSuccess = true
        } catch (e: DbException) {
            isSuccess = false
            e.printStackTrace()
        }
        return isSuccess
    }

    fun close() {
        try {
            dbManager!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}