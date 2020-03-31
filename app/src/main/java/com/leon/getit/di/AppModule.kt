package com.leon.getit.di

import android.content.Context
import com.leon.getit.MainApplication
import com.leon.getit.repository.AccountRepository
import com.leon.getit.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(application: MainApplication): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideAccountRepository(context: Context) = AccountRepository(context)

    @Singleton
    @Provides
    fun provideUserRepository() = UserRepository()

}