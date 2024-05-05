package com.novel.myapplication

import android.app.Application
import android.content.Context
import com.novel.myapplication.utils.SharePrefBase
import org.xutils.BuildConfig
import org.xutils.x

class APP :Application() {

    override fun onCreate() {
        super.onCreate()
        //xutils 初始化
        //xutils 初始化
        x.Ext.init(this)
        x.Ext.setDebug(BuildConfig.DEBUG)

    }


}