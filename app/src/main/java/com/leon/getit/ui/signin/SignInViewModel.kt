package com.leon.getit.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leon.getit.repository.AccountRepository
import com.leon.getit.repository.UserRepository
import javax.inject.Inject

class SignInViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var accountRepository: AccountRepository

    @Inject
    lateinit var userRepository: UserRepository

    val signInState: MutableLiveData<Boolean>
        get() = accountRepository.signInResult

    val signInErrorMessage: String
        get() = accountRepository.signInErrorMessage

    fun signIn(name: String, password: String) {
        accountRepository.signIn(name, password)
    }

    fun isUserInfoComplete() = userRepository.isUserInfoComplete()

    fun getCurrentUserObjectId(): String? = userRepository.getCurrentUser()?.objectId
}
