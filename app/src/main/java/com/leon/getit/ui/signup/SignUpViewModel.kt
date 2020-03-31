package com.leon.getit.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leon.getit.repository.AccountRepository
import com.leon.getit.repository.UserRepository
import javax.inject.Inject

class SignUpViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var accountRepository: AccountRepository

    @Inject
    lateinit var userRepository: UserRepository

    val signUpResult: MutableLiveData<Boolean>
        get() = accountRepository.signUpResult

    val signUpErrorMessage: String
        get() = accountRepository.signUpErrorMessage

    fun signUp(phone: String, name: String, password: String) {
        accountRepository.signUp(phone, name, password)
    }

    fun getCurrentUserObjectId(): String? = userRepository.getCurrentUser()?.objectId

}
