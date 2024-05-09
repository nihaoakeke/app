package com.novel.myapplication.activity

import android.content.Intent
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
import com.novel.myapplication.databinding.ActivityLoginBinding
import com.novel.myapplication.utils.SharePrefUtils


class LoginActivity: BaseActivity<ActivityLoginBinding>() {
    private var type = ""
    var mLogin = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }


     fun initView() {
        mViewBinding.editPwd.getText()
        mViewBinding.radioGroupUnassured.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> //获取被选中的radiobutton的id
            val rcheck = findViewById<View>(checkedId) as RadioButton
            //获取
            val checkText = rcheck.text.toString()
            type = checkText
//            SharePrefUtils.setLoginType(type)
            Toast.makeText(this@LoginActivity, "您选中的是：$checkText", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onResume() {
        super.onResume()
        mViewBinding.progressCircular.visibility =View.GONE
    }

    fun loginClick(v: View) {
        val i = v.id
        if (i == R.id.btn_login) {
            if (!TextUtils.isEmpty(type)) {
                if (type == "作家") {

                } else if (type == "读者") {
                } else if (type == "学生") {
                }
            }
            if (TextUtils.isEmpty(type)){
                Toast.makeText(this@LoginActivity, "请选择用户类型", Toast.LENGTH_LONG).show()
                return
            }
            mViewBinding.progressCircular.setVisibility(View.VISIBLE)
            if (!TextUtils.isEmpty(
                    mViewBinding.editAddress.getText().toString()
                ) && mViewBinding.editAddress.getText().toString().equals("admin")
            ) {
                if (!TextUtils.isEmpty(
                        mViewBinding.editPwd.getText().toString()
                    ) && mViewBinding.editPwd.getText().toString().equals("admin123")
                ) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    SharePrefUtils.saveName(this@LoginActivity,mViewBinding.editAddress.getText().toString())
                    SharePrefUtils.setLoginType(this@LoginActivity,type)
                    intent.putExtra("user", "admin")
                    startActivity(intent)
                }
            } else {
                if (TextUtils.isEmpty(mViewBinding.editAddress.getText().toString())) {
                    Toast.makeText(this@LoginActivity, "用户名不能为空", Toast.LENGTH_LONG).show()
                    return
                } else if (TextUtils.isEmpty(mViewBinding.editPwd.getText().toString())) {
                    Toast.makeText(this@LoginActivity, "密码不能为空", Toast.LENGTH_LONG).show()
                    return
                }
                val userDao = UserDao()
                val userBean: UserBean? =
                    userDao.getUser(mViewBinding.editAddress.getText().toString())
                if (userBean != null) {
                    if (userBean.name.toString()
                            .equals(mViewBinding.editAddress.getText().toString())
                        && userBean.pwd.toString()
                            .equals(mViewBinding.editPwd.getText().toString())
                    ) {
                        mViewBinding.progressCircular.setVisibility(View.GONE)
                        if (userBean.type.equals(type)){
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("user", userBean.name.toString())
                            SharePrefUtils.saveName(this@LoginActivity,userBean.name.toString())
                            SharePrefUtils.setLoginType(this@LoginActivity,type)
                            startActivity(intent)

                        }else{
                            Toast.makeText(
                                this@LoginActivity,
                                "账号类型不对！",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    } else {
                        mViewBinding.progressCircular.setVisibility(View.GONE)
                        Toast.makeText(
                            this@LoginActivity,
                            "类型不对或者密码错误！",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    mViewBinding.progressCircular.setVisibility(View.GONE)
                    Toast.makeText(this@LoginActivity, "还未注册 ，请先注册！", Toast.LENGTH_LONG)
                        .show()
                }
            }


//                    }
//                });
        } else if (i == R.id.btn_register) {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        } else if (i == R.id.btn_forget_pwd) {
//                forgetPwd();
        } else if (i == R.id.btn_tip) {
//                forwardTip();
        }
    }


    fun loginBtn():String{
        return ""
    }
}


