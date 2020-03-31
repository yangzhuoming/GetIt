package com.leon.getit.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.leon.getit.R
import com.leon.getit.databinding.FragmentSignUpBinding
import com.leon.getit.util.OBJECT_ID
import com.leon.lib_base.BaseFragment
import com.leon.lib_base.findNavController
import com.leon.lib_base.viewModelProvider
import org.jetbrains.anko.toast
import javax.inject.Inject

class SignUpFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: SignUpViewModel
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = viewModelProvider(viewModelFactory)
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = model
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.signUpResult.observe(viewLifecycleOwner, Observer {
            if (it) {
                activity!!.toast(R.string.sign_up_success)
                val bundle = Bundle()
                bundle.putString(OBJECT_ID, model.getCurrentUserObjectId())
                findNavController().navigate(R.id.action_signUpFragment_to_userInfoFragment, bundle)
            } else {
                activity!!.toast(getString(R.string.sign_up_failed) + ":" + model.signUpErrorMessage)
            }
        })

        binding.imgBack.setOnClickListener {
            backToSignIn()
        }
        binding.txtBack.setOnClickListener {
            backToSignIn()
        }

        binding.btnSignUp.setOnClickListener {
            val userName = binding.edtUserName.text.toString().trim()
            val phone = binding.edtPhone.text.toString().trim()
            val password = binding.edtPwd.text.toString().trim()
            val passwordCom = binding.edtPwdConfirm.text.toString().trim()
            if (userName.isEmpty() || password.isEmpty() || passwordCom.isEmpty() || phone.isEmpty()) {
                activity!!.toast(R.string.phone_name_and_pwd_cant_be_empty)
                return@setOnClickListener
            } else if(password != passwordCom) {
                activity!!.toast(R.string.pwd_not_match)
                return@setOnClickListener
            }
            model.signUp(phone, userName, password)
        }

    }

    /**
     * 返回到登录界面
     */
    private fun backToSignIn() {
        findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
    }

}
