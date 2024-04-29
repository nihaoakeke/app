package com.novel.myapplication.activity

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.novel.myapplication.R
import com.novel.myapplication.adapter.MainPagerAdapter
import com.novel.myapplication.base.BaseActivity
import com.novel.myapplication.databinding.ActivityMainBinding
import com.novel.myapplication.fragment.HomeFragment
import com.novel.myapplication.fragment.MeFragment

class MainActivity :BaseActivity<ActivityMainBinding> (){

    private  val TAG = "MainActivity"
   var  PERMISSION_REQUESTS:Int =100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this.allPermissionsGranted()) {
            this.getRuntimePermissions()
        }
        initView()
    }
    fun  initView(){
        val fragments: MutableList<Fragment> = ArrayList()
        fragments.add(HomeFragment())
        fragments.add(MeFragment())
//        PhoneInfo phoneInfo = new PhoneInfo(this);

        //        PhoneInfo phoneInfo = new PhoneInfo(this);
        val adapter = MainPagerAdapter(supportFragmentManager, fragments)

        mViewBinding.mainPager.setOffscreenPageLimit(3)
        mViewBinding.mainPager.setAdapter(adapter)
        if (true) {
//            mViewBinding.tabHome.setText("首页");
//            mViewBinding.tabDone.setText("人员信息");
//            mViewBinding.tabTo.setText("人员信息");
        }
        mViewBinding.radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.tab_home -> mViewBinding.mainPager.setCurrentItem(0, false)
                R.id.tab_to -> mViewBinding.mainPager.setCurrentItem(1, false)
                R.id.tab_done -> mViewBinding.mainPager.setCurrentItem(2, false)
                R.id.tab_me -> mViewBinding.mainPager.setCurrentItem(3, false)
            }
        })
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                return false
            }
        }
        return true
    }


    private fun getRequiredPermissions(): Array<String?> {
        return try {
            val info = this.packageManager
                .getPackageInfo(this.packageName, PackageManager.GET_PERMISSIONS)
            val ps = info.requestedPermissions
            if (ps != null && ps.size > 0) {
                ps
            } else {
                arrayOfNulls(0)
            }
        } catch (e: RuntimeException) {
            throw e
        } catch (e: Exception) {
            arrayOfNulls(0)
        }
    }
    private fun getRuntimePermissions() {
        val allNeededPermissions: MutableList<String> = java.util.ArrayList()
        for (permission in this.getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                if (permission != null) {
                    allNeededPermissions.add(permission)
                }
            }
        }
        if (!allNeededPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(
                this, allNeededPermissions.toTypedArray<String>(), PERMISSION_REQUESTS
            )
        }
    }

    private fun isPermissionGranted(context: Context, permission: String?): Boolean {
        if (ContextCompat.checkSelfPermission(context, permission!!)
            == PackageManager.PERMISSION_GRANTED
        ) {
            Log.i(TAG, "Permission granted: $permission")
            return true
        }
        //        getRuntimePermissions();
        Log.i(TAG, "Permission NOT granted: $permission")
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != PERMISSION_REQUESTS) {
            return
        }
    }

}