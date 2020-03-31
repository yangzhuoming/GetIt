package com.leon.getit.data

import cn.bmob.v3.BmobUser
import com.leon.getit.util.SEX_MAN

class User : BmobUser() {

    /**
     * 昵称
     */
    lateinit var nickname: String

    /**
     * 自我介绍
     */
    var desc: String = ""

    /**
     * 性别
     */
    var sex: String = SEX_MAN

    /**
     * 头像
     */
    var portrait: String = ""

    /**
     * 年龄
     */
    var age: Int = 0

    /**
     * 生日
     */
    var birthDay: Long = 0

}