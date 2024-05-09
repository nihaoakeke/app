package com.novel.myapplication.utils

import android.content.Context

 object SharePrefUtils : SharePrefBase() {

    /**
     * sp存放字段的key
     */
    interface SPKey {
        companion object {
            const val USER_ID = "userId"
            const val USER_NAME = "name"
            const val LOGIN_TYPE = "type"
            const val LOGIN_USER_IMG = "user_img"
        }
    }



    fun getName(context: Context): String? {
        return getString(context, SPKey.USER_NAME, "")
    }

    fun saveName(context: Context,username: String?) {
        saveString(context , SPKey.USER_NAME, username)
    }
    fun getUserImg(context: Context): String? {
        return getString(context, SPKey.LOGIN_USER_IMG, "")
    }

    fun saveUserImg(context: Context,username: String?) {
        saveString(context , SPKey.LOGIN_USER_IMG, username)
    }

    fun getLoginType(context: Context): String? {
        return getString(context, SPKey.LOGIN_TYPE, "")
    }

    fun setLoginType(context: Context,type: String?) {
        saveString(context, SPKey.LOGIN_TYPE, type)
    }
}