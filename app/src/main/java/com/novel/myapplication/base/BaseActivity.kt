package com.novel.myapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

open class BaseActivity <VB : ViewBinding>:AppCompatActivity(){
    protected lateinit var mViewBinding: VB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initLayout()
    }

//    open fun initView(){
////        mViewBinding = ()
//        setContentView(mViewBinding?.root)
//    }

    protected fun initLayout() {
        // 这里为了适配，不使用 ViewBinding
            // 通过反射创建 VB
            val genericSuperclass = javaClass.genericSuperclass as? ParameterizedType
            val actualTypeArguments = genericSuperclass?.actualTypeArguments
            val type = actualTypeArguments?.get(0)
            val mVB = type as? Class<*>
            if (mVB != null && ViewBinding::class.java.isAssignableFrom(mVB)) {
                try {
                    val method = mVB.getMethod("inflate", LayoutInflater::class.java)
                    @Suppress("UNCHECKED_CAST")
                    mViewBinding = method.invoke(null, layoutInflater) as VB
                    setContentView(mViewBinding?.root)
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }
        }
    }


}