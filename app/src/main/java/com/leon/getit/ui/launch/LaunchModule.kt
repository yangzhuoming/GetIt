package com.leon.getit.ui.launch

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
internal abstract class LaunchModule {

    @ContributesAndroidInjector
    internal abstract fun contributeLaunchFragment(): LaunchFragment

    /**
     * ViewModel被Dagger创建在map中。通过@ViewModelKey，我们定义了我们
     * 想要获得一个[LaunchViewModel]类。
     */
    @Binds
    @IntoMap
    @ViewModelKey(LaunchViewModel::class)
    internal abstract fun bindLaunchViewModel(viewModel: LaunchViewModel) : ViewModel

}