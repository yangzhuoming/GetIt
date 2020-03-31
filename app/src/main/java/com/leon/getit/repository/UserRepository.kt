package com.leon.getit.repository

import androidx.lifecycle.MutableLiveData
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.leon.getit.data.Friend
import com.leon.getit.data.User
import java.text.SimpleDateFormat
import java.util.*

class UserRepository {

    var userInfoUpdateResult: MutableLiveData<Boolean> = MutableLiveData()
    var userInfoUpdateErrorMessage = ""

    val searchUserByNameResult: MutableLiveData<List<User>> = MutableLiveData()
    var searchUserByNameErrorMsg = ""

    val searchUserByPhoneResult: MutableLiveData<List<User>> = MutableLiveData()
    var searchUserByPhoneErrorMsg = ""

    val searchUserRecentResult: MutableLiveData<List<User>> = MutableLiveData()
    var searchUserRecentErrorMsg = ""

    val searchUserByIdResult: MutableLiveData<User> = MutableLiveData()
    var searchUserByIdErrorMsg = ""

    val isMyFriendResult: MutableLiveData<Boolean> = MutableLiveData()
    var isMyFriendErrorMsg = ""

    val addFriendResult: MutableLiveData<Boolean> = MutableLiveData()
    var addFriendErrorMsg = ""

    val searchFriendResult: MutableLiveData<List<Friend>> = MutableLiveData()
    var searchFriendErrorMsg = ""

    /**
     * 获取当前用户
     */
    fun getCurrentUser(): User? {
        return BmobUser.getCurrentUser(User::class.java)
    }

    /**
     * 用户信息是否完善
     */
    fun isUserInfoComplete(): Boolean {
        val user = getCurrentUser()
        return if (null != user) {
            user.desc.isNotEmpty() && user.portrait.isNotEmpty()
        } else {
            false
        }
    }

    /**
     * 更新用户信息
     */
    fun updateUserInfo(portrait: String, desc: String, sex: String, birthday: Long) {
        val user = getCurrentUser()
        if (null != user) {
            user.portrait = portrait
            user.desc = desc
            user.sex = sex
            user.birthDay = birthday
            user.update(object: UpdateListener() {
                override fun done(e: BmobException?) {
                    if (null == e) {
                        userInfoUpdateResult.value = true
                    } else {
                        userInfoUpdateErrorMessage = e.message!!
                        userInfoUpdateResult.value = false
                    }
                }
            })
        }
    }

    /**
     * 注销当前用户
     */
    fun logOut() {
        BmobUser.logOut()
    }

    /**
     * 获取用户达人天数
     */
    fun getBigGun(user: User): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val createDate = sdf.parse(user.createdAt)
        val now = Date()
        val day = ((now.time - createDate.time) / (3600*1000*24)).toInt()
        return if (0 == day) 1 else day
    }

    /**
     * 获取当前用户手机号
     */
    fun getPhoneNumber(): String {
        val user = getCurrentUser()
        return user?.mobilePhoneNumber?.replaceRange(3, 8, "*****") ?: ""
    }

    /**
     * 通过用户名查找用户（不支持模糊查询）
     */
    fun searchUserByName(userName: String) {
        val query: BmobQuery<User> = BmobQuery()
        query.setLimit(8)
            .addWhereEqualTo("username", userName)
            .findObjects(object: FindListener<User>() {
                override fun done(userList: MutableList<User>?, e: BmobException?) {
                    if (null == e) {
                        searchUserByNameResult.value = userList
                    } else {
                        searchUserByNameErrorMsg = e.message!!
                    }
                }
            })
    }

    /**
     * 通过手机号查找用户（不支持模糊查询）
     */
    fun searchUserByPhone(phone: String) {
        val query: BmobQuery<User> = BmobQuery()
        query.setLimit(8)
            .addWhereEqualTo("mobilePhoneNumber", phone)
            .findObjects(object: FindListener<User>() {
                override fun done(userList: MutableList<User>?, e: BmobException?) {
                    if (null == e) {
                        searchUserByPhoneResult.value = userList
                    } else {
                        searchUserByPhoneErrorMsg = e.message!!
                    }
                }
            })
    }

    /**
     * 通过用户ID查找用户
     */
    fun searchUserById(userId: String) {
        val query: BmobQuery<User> = BmobQuery()
        query.setLimit(1)
            .addWhereEqualTo("objectId", userId)
            .findObjects(object: FindListener<User>() {
                override fun done(userList: MutableList<User>?, e: BmobException?) {
                    if (null == e) {
                        if(null != userList) {
                            searchUserByIdResult.value = userList[0]
                        }
                    } else {
                        searchUserByIdErrorMsg = e.message!!
                    }
                }
            })
    }

    /**
     * 查找最近注册的用户
     */
    fun searchUserRecentSignUp() {
        val query: BmobQuery<User> = BmobQuery()
        query.setLimit(10)
            .addWhereNotEqualTo("objectId", getCurrentUser()!!.objectId)//排除当前用户
            .order("-createdAt")
            .findObjects(object: FindListener<User>() {
                override fun done(userList: MutableList<User>?, e: BmobException?) {
                    if (null == e) {
                        searchUserRecentResult.value = userList
                    } else {
                        searchUserRecentErrorMsg = e.message!!
                    }
                }
            })
    }

    /**
     * 查看某个用户是否为当前用户好友
     */
    fun isMyFriend(user: User) {
        val query: BmobQuery<Friend> = BmobQuery()
        query.setLimit(1)
            .addWhereEqualTo("user", getCurrentUser())
            .addWhereEqualTo("friendUser", user)
            .order("-updatedAt")
            .findObjects(object : FindListener<Friend>() {
                override fun done(list: MutableList<Friend>?, e: BmobException?) {
                    if (null == e) {
                        isMyFriendResult.value = !list.isNullOrEmpty()
                    } else {
                        isMyFriendErrorMsg = e.message!!
                    }
                }
            })
    }

    /**
     * 添加好友
     */
    fun addFriend(uid: String) {
        val friendUser = User()
        friendUser.objectId = uid
        val friend = Friend()
        friend.user = getCurrentUser()!!
        friend.friendUser = friendUser
        friend.save(object: SaveListener<String>(){
            override fun done(s: String?, e: BmobException?) {
                if (null == e) {
                    addFriendResult.value = true
                } else {
                    addFriendErrorMsg = e.message!!
                    addFriendResult.value = false
                }
            }
        })
    }

    /**
     * 查找好友
     */
    fun searchFriends() {
        val query: BmobQuery<Friend> = BmobQuery()
        query.addWhereEqualTo("user", getCurrentUser())
        query.include("friendUser")
        query.order("-updatedAt")
        query.findObjects(object: FindListener<Friend>() {
                override fun done(list: MutableList<Friend>, e: BmobException?) {
                    if (null == e) {
                        if (list.size > 0) {
                            searchFriendResult.value = list
                        }
                    } else {
                        searchFriendErrorMsg = e.message!!
                    }
                }
            })
    }

    /**
     * 删除好友
     */
    fun deleteFriend() {

    }

}