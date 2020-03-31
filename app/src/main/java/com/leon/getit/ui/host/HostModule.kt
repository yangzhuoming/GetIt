package com.leon.getit.ui.host

import androidx.lifecycle.ViewModel
import com.leon.lib_base.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * 模块中应用程序启动所需要的类
 */
@Module
internal abstract class HostModule {

    @ContributesAndroidInjector
    internal abstract fun contributeHostFragment(): HostFragment

    /**
     * ViewModel被Dagger创建在map中。通过@ViewModelKey，我们定义了我们
     * 想要获得一个[HostViewModel]类。
     */
    @Binds
    @IntoMap
    @ViewModelKey(HostViewModel::class)
    internal abstract fun bindHostViewModel(viewModel: HostViewModel) : ViewModel

}