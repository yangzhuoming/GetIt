package com.leon.lib_base

import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.android.support.DaggerFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.AnkoLogger

open class BaseFragment:
    DaggerFragment(),
    AnkoLogger
{

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    //TODO
    @Subscribe
    fun onEmptyEvent(event: EmptyEvent) {

    }

    companion object {
        fun <T: Fragment> newInstance(clazz: Class<out BaseFragment>, args: Bundle?): T {
            var fragment: T? = null
            try {
                fragment = clazz.newInstance() as T
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
            fragment?.arguments = args
            return fragment!!
        }
    }

}