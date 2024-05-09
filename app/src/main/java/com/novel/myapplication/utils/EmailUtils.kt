package com.novel.myapplication.utils

import java.util.regex.Pattern

object EmailUtils {

    /**
     * From androidx.core.util.PatternsCompat.EMAIL_ADDRESS
     */
    val EMAIL_ADDRESS: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )



    /**
     * 检查字符串值是否为电子邮件
     *
     * @return 如果字符串是电子邮件地址，则为 true
     */
    fun validateEmail(email:String): Boolean = EMAIL_ADDRESS.matcher(email).matches()
}