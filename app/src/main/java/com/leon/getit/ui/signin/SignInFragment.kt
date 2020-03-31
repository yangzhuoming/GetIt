package com.leon.getit.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.leon.getit.R
import com.leon.getit.databinding.FragmentSignInBinding
import com.leon.getit.util.OBJECT_ID
import com.leon.lib_base.BaseFragment
import com.leon.lib_base.findNavController
import com.leon.lib_base.viewModelProvider
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class SignInFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: SignInViewModel

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = viewModelProvider(viewModelFactory)
        binding = FragmentSignInBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = model
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.signInState.observe(viewLifecycleOwner, Observer {
            //loadingView.hide()
            if (it) {
                activity!!.toast(R.string.sign_in_success)
                if (model.isUserInfoComplete()) {
                    findNavController().navigate(R.id.action_signInFragment_to_hostFragment)
                } else {
                    if (null != model.getCurrentUserObjectId()) {
                        val bundle = Bundle()
                        bundle.putString(OBJECT_ID, model.getCurrentUserObjectId())
                        findNavController().navigate(R.id.action_signInFragment_to_userInfoFragment)
                    }
                }
            } else {
                activity!!.toast(getString(R.string.sign_in_failed) + ":" + model.signInErrorMessage)
            }
        })

        binding.tvGoToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        binding.btnSignIn.setOnClickListener {
            val accountName = edt_account_name.text.toString().trim()
            val password = edt_account_pwd.text.toString().trim()
            if (accountName.isEmpty() || password.isEmpty()) {
                activity!!.toast(R.string.name_and_pwd_cant_be_empty)
            } else {
                model.signIn(accountName, password)
                //activity.loadingView.setLoadingText(getString(R.string.sign_in_ing_please_wait))
                //loadingView.show()
            }
        }

    }



}
