package com.leon.getit.data

import cn.bmob.v3.BmobObject

/**
 * 好友关系表
 */
class Friend: BmobObject() {

    lateinit var user: User

    lateinit var friendUser: User


}