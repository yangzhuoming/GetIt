package com.leon.getit.di

import com.leon.getit.MainActivity
import com.leon.getit.ui.host.HostModule
import com.leon.getit.ui.launch.LaunchModule
import com.leon.getit.ui.signin.SignInModule
import com.leon.getit.ui.signup.SignUpModule
import com.leon.getit.ui.user_info.UserInfoModule
import com.leon.lib_base.di.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            LaunchModule::class,
            SignInModule::class,
            SignUpModule::class,
            UserInfoModule::class,
            HostModule::class
        ]
    )
    internal abstract fun mainActivity(): MainActivity

}