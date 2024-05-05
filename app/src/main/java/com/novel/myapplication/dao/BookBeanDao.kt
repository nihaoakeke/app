package com.novel.myapplication.dao

import com.novel.myapplication.bean.BookBean
import com.novel.myapplication.db.DatabaseOpenHelper
import org.xutils.DbManager
import org.xutils.common.util.KeyValue
import org.xutils.db.sqlite.WhereBuilder
import org.xutils.ex.DbException
import java.io.IOException

class BookBeanDao {


    private val TAG = BookBeanDao::class.java.simpleName
    private var dbManager: DbManager? = null
    private var isSuccess = false
    private var contactList: List<BookBean>? = null
    constructor()  {
        dbManager = DatabaseOpenHelper.getInstance()
    }

    //保存书籍
    fun addOrUpdate(bookBean: BookBean) {
        try {
            if (dbManager== null){
                dbManager = DatabaseOpenHelper.getInstance()
            }
            val contact: BookBean? = dbManager?.findById(bookBean::class.java, bookBean.id)
            if (contact == null) {
                dbManager!!.save(bookBean)
            } else {
                val builder = WhereBuilder.b()
                builder.and("id", "=", bookBean.id)
                val key1 = KeyValue("bookName", bookBean.bookName)
                val key2 = KeyValue("picture", bookBean.picture)
                val key3 = KeyValue("author", bookBean.author)
                val key4 = KeyValue("time", bookBean.time)
                val key5 = KeyValue("book_type", bookBean.book_type)
                //                builder.and("id", "=", ContextBean.getId());
//
                dbManager!!.update(bookBean::class.java, builder, key1, key2, key3,key4,key5)
                //                dbManager.update(contact);
            }
        } catch (e: DbException) {
            e.printStackTrace()
        }
    }

    //根据条件查找数据
    fun getContactAll2(): List<BookBean>? {
        try {
//            String user_id = LoginManager.getUserId();
            contactList = dbManager!!.selector(BookBean::class.java).findAll()
        } catch (e: DbException) {
            e.printStackTrace()
        }
        return contactList
    }

    //查找全部数据
    fun getContactAll(): List<BookBean>? {
        try {
            contactList = dbManager!!.selector(BookBean::class.java).findAll()
        } catch (e: DbException) {
            e.printStackTrace()
        }
        return contactList
    }

    fun getBook(name: String?): BookBean? {
        var bookBean: BookBean? = null
        try {
            val builder = WhereBuilder.b()
            builder.and("name", "=", name)
            bookBean = dbManager!!.selector(BookBean::class.java).where(
                "name",
                "=", name
            ).findFirst()
        } catch (e: DbException) {
            e.printStackTrace()
        }
        return bookBean
    }

    fun delete(ContextBean: BookBean?): Boolean {
        try {
            dbManager!!.delete(ContextBean)
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