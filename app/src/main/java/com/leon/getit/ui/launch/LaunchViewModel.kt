package com.leon.getit.ui.launch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leon.getit.repository.AccountRepository
import com.leon.getit.repository.UserRepository
import javax.inject.Inject

class LaunchViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var accountRepository: AccountRepository
    @Inject
    lateinit var userRepository: UserRepository

    val connectResult: MutableLiveData<Boolean>
        get() = accountRepository.connectResult
    val connectErrorMsg: String
        get() = accountRepository.connectErrorMsg

    var isLogin: MutableLiveData<Boolean> = MutableLiveData()

    fun checkUserIsLogin() {
        isLogin.value = accountRepository.isUserLogin()
    }

    fun isUserInfoComplete() = userRepository.isUserInfoComplete()

    fun connect() = accountRepository.connect()
}
