package com.leon.getit.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import cn.bmob.newim.BmobIM
import cn.bmob.newim.bean.BmobIMUserInfo
import cn.bmob.newim.listener.ConnectListener
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.SaveListener
import com.leon.getit.data.User

/**
 * 账户仓库类
 * 用于用户的账户操作，如注册、登录、连接服务器、断开服务器等等
 */
class AccountRepository constructor(val context: Context) {

    val signUpResult: MutableLiveData<Boolean> = MutableLiveData()
    var signUpErrorMessage = ""
    val signInResult: MutableLiveData<Boolean> = MutableLiveData()
    var signInErrorMessage = ""
    val connectResult: MutableLiveData<Boolean> = MutableLiveData()
    var connectErrorMsg = ""

    /**
     * 用户注册
     */
    fun signUp(phone: String, userName: String, password: String) {
        val user = User()
        user.mobilePhoneNumber = phone
        user.username = userName
        user.setPassword(password)

        user.signUp(object: SaveListener<User>() {
            override fun done(user: User?, e: BmobException?) {
                if (null == e) {
                    signUpResult.value = true
                } else {
                    signUpErrorMessage = e.message!!
                    signUpResult.value = false
                }
            }
        })
    }

    /*
     * 用户登录
     */
    fun signIn(userName: String, password: String) {
        BmobUser.loginByAccount(userName, password, object: LogInListener<User>() {
            override fun done(user: User?, e: BmobException?) {
                if (null == e) {
                    signInResult.value = true
                } else {
                    signInErrorMessage = e.message!!
                    signInResult.value = false
                }
            }
        })
    }

    /**
     * 登录成功、注册成功或处于登录状态重新打开应用后执行连接IM服务器的操作
     */
    fun connect() {
        val user = BmobUser.getCurrentUser(User::class.java)
        if (user.objectId.isNotEmpty()) {
            BmobIM.connect(user.objectId, object: ConnectListener() {
                override fun done(uid: String?, e: BmobException?) {
                    if (null == e) {
                        //更新用户资料，用于在会话页面、聊天页面以及个人信息页面显示
                        BmobIM.getInstance().updateUserInfo(
                            BmobIMUserInfo(user.objectId, user.username, user.portrait)
                        )
                        connectResult.value = true
                    } else {
                        connectResult.value = false
                        connectErrorMsg = e.message!!
                    }
                }
            })
        }
    }

    fun disConnect() {
        BmobIM.getInstance().disConnect()
    }

    fun isUserLogin() = BmobUser.isLogin()

}