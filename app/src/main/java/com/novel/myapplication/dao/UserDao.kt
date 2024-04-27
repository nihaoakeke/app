package com.novel.myapplication.dao

import com.novel.myapplication.bean.UserBean
import com.novel.myapplication.db.DatabaseOpenHelper
import org.xutils.DbManager
import org.xutils.common.util.KeyValue
import org.xutils.db.sqlite.WhereBuilder
import org.xutils.ex.DbException
import java.io.IOException

class UserDao {


    private val TAG = UserDao::class.java.simpleName
    private var dbManager: DbManager? = null
    private var isSuccess = false
    private var contactList: List<UserBean>? = null
    constructor()  {
        dbManager = DatabaseOpenHelper.getInstance()
    }

    //保存或者更新联系人数据
    fun addOrUpdate(userBean: UserBean) {
        try {
            if (dbManager== null){
                dbManager = DatabaseOpenHelper.getInstance()
            }
            val contact: UserBean? = dbManager?.findById(UserBean::class.java, userBean.id)
            if (contact == null) {
                dbManager!!.save(userBean)
            } else {
                val builder = WhereBuilder.b()
                builder.and("id", "=", userBean.id)
                val key1 = KeyValue("name", userBean.name)
                val key2 = KeyValue("phone", userBean.phone)
                val key3 = KeyValue("pwd", userBean.pwd)
                val key4 = KeyValue("address", userBean.address)
                //                builder.and("id", "=", ContextBean.getId());
//
                dbManager!!.update(UserBean::class.java, builder, key1, key2, key3,key4)
                //                dbManager.update(contact);
            }
        } catch (e: DbException) {
            e.printStackTrace()
        }
    }

    //根据条件查找数据
    fun getContactAll2(): List<UserBean>? {
        try {
//            String user_id = LoginManager.getUserId();
            contactList = dbManager!!.selector(UserBean::class.java).findAll()
        } catch (e: DbException) {
            e.printStackTrace()
        }
        return contactList
    }

    //查找全部数据
    fun getContactAll(): List<UserBean>? {
        try {
            contactList = dbManager!!.selector(UserBean::class.java).findAll()
        } catch (e: DbException) {
            e.printStackTrace()
        }
        return contactList
    }

    fun getUser(name: String?): UserBean? {
        var userBean: UserBean? = null
        try {
            val builder = WhereBuilder.b()
            builder.and("name", "=", name)
            userBean = dbManager!!.selector(UserBean::class.java).where(
                "name",
                "=", name
            ).findFirst()
        } catch (e: DbException) {
            e.printStackTrace()
        }
        return userBean
    }

    fun delete(ContextBean: UserBean?): Boolean {
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