package com.leon.getit.ui.signup

import androidx.lifecycle.ViewModel
import com.leon.lib_base.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class SignUpModule {

    @ContributesAndroidInjector
    internal abstract fun contributeSignUpFragment(): SignUpFragment

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    internal abstract fun bindSignUpViewModel(viewModel: SignUpViewModel): ViewModel

}