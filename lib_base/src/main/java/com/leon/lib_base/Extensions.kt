package com.leon.lib_base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

/**
 * 在不是[NavHostFragment]或不在[NavHostFragment]中的Fragment去调用该函数会导致[IllegalStateException]
 */
fun Fragment.findNavController(): NavController =
    NavHostFragment.findNavController(this)

/**
 * 可以在[FragmentActivity]中这样使用
 * val myViewModel = viewModelProvider(myViewModelFactory)
 */
inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(this, provider).get(VM::class.java)

/**
 * 可以在[Fragment]中这样使用
 * val myViewModel = viewModelProvider(myViewModelFactory)
 */
inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(this, provider).get(VM::class.java)

