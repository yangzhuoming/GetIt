package com.leon.getit.ui.launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.leon.getit.R
import com.leon.lib_base.BaseFragment
import com.leon.lib_base.findNavController
import com.leon.lib_base.viewModelProvider
import org.jetbrains.anko.toast
import javax.inject.Inject

class LaunchFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LaunchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        return inflater.inflate(R.layout.fragment_launch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLogin.observe(viewLifecycleOwner, Observer {
            if(it) {//有登录用户
                if (viewModel.isUserInfoComplete()) {//如果用户信息完善
                    viewModel.connect()
                } else {//如果用户信息不完善,跳转到登陆界面
                    findNavController().navigate(R.id.action_launchFragment_to_signInFragment)
                }
            } else {//无登录用户
                findNavController().navigate(R.id.action_launchFragment_to_signInFragment)
            }
        })

        viewModel.connectResult.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(R.id.action_launchFragment_to_hostFragment)
            } else {
                activity!!.toast(getString(R.string.connect_to_server_failed) + viewModel.connectErrorMsg)
                activity!!.finish()
            }
        })

        viewModel.checkUserIsLogin()
    }

}
