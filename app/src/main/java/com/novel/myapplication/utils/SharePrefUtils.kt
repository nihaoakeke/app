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
        }
    }



    fun getName(context: Context): String? {
        return getString(context, SPKey.USER_NAME, "")
    }

    fun saveName(context: Context,username: String?) {
        saveString(context , SPKey.USER_NAME, username)
    }

    fun getLoginType(context: Context): String? {
        return getString(context, SPKey.LOGIN_TYPE, "")
    }

    fun setLoginType(context: Context,type: String?) {
        saveString(context, SPKey.LOGIN_TYPE, type)
    }
}