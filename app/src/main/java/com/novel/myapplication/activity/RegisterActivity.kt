package com.novel.myapplication.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.novel.myapplication.R
import com.novel.myapplication.base.BaseActivity
import com.novel.myapplication.bean.UserBean
import com.novel.myapplication.dao.UserDao
import com.novel.myapplication.databinding.ActivityRegisterBinding

class RegisterActivity  : BaseActivity<ActivityRegisterBinding>() {
    var type: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding.title.btnBack.setOnClickListener{
            finish()
        }
    }

    fun registerClick(v: View) {
        val i = v.id
        if (i == R.id.btn_code) {
//            getCode();
        } else if (i == R.id.btn_register) {
            val userDao = UserDao()
            val userBean: UserBean? = userDao.getUser(mViewBinding.editUser.text.toString())
            if (userBean != null) {
                Toast.makeText(this, "改用户名已存在,请换其他账号试试", Toast.LENGTH_LONG).show()
                return
            }
            val bean = UserBean()
            bean.id=  System.currentTimeMillis().toString() + ""
            bean.stuNum = mViewBinding.stuNum.text.toString()
            bean.birthday =(mViewBinding.birthdayEdit.text.toString())
            bean.phone =mViewBinding.editPwd1.text.toString()
            bean.type = type
            bean.name =mViewBinding.editUser.text.toString()
            bean.pwd =mViewBinding.editPwd1.text.toString()
//            bean.setAddress(stuNum.getText().toString())
//            bean.setLevel(level)
            userDao.addOrUpdate(bean)
            Toast.makeText(this, "注册成功,跳转登录", Toast.LENGTH_LONG).show()
            finish()
            //            register();
        }
    }
}