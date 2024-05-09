package com.novel.myapplication.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.novel.myapplication.R
import com.novel.myapplication.base.BaseActivity
import com.novel.myapplication.bean.UserBean
import com.novel.myapplication.dao.UserDao
import com.novel.myapplication.databinding.ActivityRegisterBinding
import com.novel.myapplication.utils.EmailUtils

class RegisterActivity  : BaseActivity<ActivityRegisterBinding>() {
    var type: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding.title.btnBack.setOnClickListener{
            finish()
        }
        mViewBinding.editUser.onFocusChangeListener =  View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                val email = mViewBinding.editUser.text.toString().trim()

                if (EmailUtils.validateEmail(email)) {
//                    Toast.makeText(this, "邮箱格式正确", Toast.LENGTH_SHORT).show()
                    mViewBinding.tips.visibility=View.GONE
                } else {
                    mViewBinding.tips.visibility=View.VISIBLE
//                    Toast.makeText(this, "请输入有效的邮箱地址", Toast.LENGTH_SHORT).show()
                }
            }
        }

        mViewBinding.radioGroupUnassured.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> //获取被选中的radiobutton的id
            val rcheck = findViewById<View>(checkedId) as RadioButton
            //获取
            val checkText = rcheck.text.toString()
            type = checkText
//            SharePrefUtils.setLoginType(type)
            Toast.makeText(this@RegisterActivity, "您选中的是：$checkText", Toast.LENGTH_SHORT).show()
        })
    }

    fun registerClick(v: View) {
        val i = v.id
        if (i == R.id.btn_code) {
//            getCode();
        } else if (i == R.id.btn_register) {
            if (TextUtils.isEmpty(type)){
                Toast.makeText(this, "请选择用户角色！", Toast.LENGTH_LONG).show()
                return
            }
            val userDao = UserDao()
            val userBean: UserBean? = userDao.getUser(mViewBinding.editUser.text.toString())
            if (userBean != null) {
                Toast.makeText(this, "改用户名已存在,请换其他账号试试", Toast.LENGTH_LONG).show()
                return
            }
           if ( !EmailUtils.validateEmail(mViewBinding.editUser.text.toString())){
               Toast.makeText(this, "请输入有效的邮箱地址", Toast.LENGTH_SHORT).show()
               return
           }
            val bean = UserBean()
            bean.id=  System.currentTimeMillis().toString() + ""
            bean.stuNum = mViewBinding.stuNum.text.toString()
            bean.birthday =(mViewBinding.birthdayEdit.text.toString())
            bean.phone =mViewBinding.editPhone.text.toString()
            bean.type = type
            bean.name =mViewBinding.editUser.text.toString()
            bean.pwd =mViewBinding.editPwd1.text.toString()
            bean.introduce =mViewBinding.introduce.text.toString()
//            bean.setAddress(stuNum.getText().toString())
//            bean.setLevel(level)
            userDao.addOrUpdate(bean)
            Toast.makeText(this, "注册成功,跳转登录", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}