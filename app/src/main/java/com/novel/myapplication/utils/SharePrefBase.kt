package com.novel.myapplication.utils

import android.content.Context
import android.content.SharedPreferences
import com.novel.myapplication.APP

open class SharePrefBase {
    private val SP_NAME = "config"
    private var sp: SharedPreferences? = null
    lateinit var context:Context
    private fun getInstance(context: Context): SharedPreferences? {
        if (sp == null) sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp
    }

    /**
     * 保存布尔值
     */
    fun saveBoolean(context: Context, key: String?, value: Boolean) {
        getInstance(context)!!.edit().putBoolean(key, value).commit()
    }

    /**
     * 保存字符串
     */
    fun saveString(context: Context, key: String?, value: String?) {
        getInstance(context)!!.edit().putString(key, value).commit()
    }

    fun deleteKey(context: Context, key: String?) {
        getInstance(context)!!.edit().putString(key, "").commit()
    }

//    fun clear() {
//        getInstance(APP.getContext())!!.edit().clear().commit()
//    }

    /**
     * 保存long型
     */
    fun saveLong(context: Context, key: String?, value: Long) {
        getInstance(context)!!.edit().putLong(key, value).commit()
    }

    /**
     * 保存int型
     */
    fun saveInt(context: Context, key: String?, value: Int) {
        getInstance(context)!!.edit().putInt(key, value).commit()
    }

    /**
     * 保存float型
     */
    fun saveFloat(context: Context, key: String?, value: Float) {
        getInstance(context)!!.edit().putFloat(key, value).commit()
    }

    /**
     * 获取字符值
     */
    fun getString(context: Context, key: String?, defValue: String?): String? {
        return getInstance(context)!!.getString(key, defValue)
    }

    /**
     * 获取int值
     */
    fun getInt(context: Context, key: String?, defValue: Int): Int {
        return getInstance(context)!!.getInt(key, defValue)
    }

    /**
     * 获取long值
     */
    fun getLong(context: Context, key: String?, defValue: Long): Long {
        return getInstance(context)!!.getLong(key, defValue)
    }

    /**
     * 获取float值
     */
    fun getFloat(context: Context, key: String?, defValue: Float): Float {
        return getInstance(context)!!.getFloat(key, defValue)
    }

    /**
     * 获取布尔值
     */
    fun getBoolean(context: Context, key: String?, defValue: Boolean): Boolean {
        return getInstance(context)!!.getBoolean(key, defValue)
    }
}