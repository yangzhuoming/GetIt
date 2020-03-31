package com.leon.getit.ui.user_info

import androidx.lifecycle.ViewModel
import com.leon.lib_base.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class UserInfoModule {

    @ContributesAndroidInjector
    internal abstract fun contributeUserInfoFragment(): UserInfoFragment

    @Binds
    @IntoMap
    @ViewModelKey(UserInfoViewModel::class)
    internal abstract fun bindUserInfoViewModel(viewModel: UserInfoViewModel): ViewModel

}