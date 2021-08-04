package com.example.ft_hangouts.presentation.navigation.router

import androidx.navigation.NavController
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation
import com.example.ft_hangouts.presentation.navigation.base.GoToScreen

abstract class BaseRouter<Navigation : BaseNavigation.Navigate>(
    protected val navController: NavController
) : GoToScreen<Navigation> {

    protected fun goToPrevious() {
        navController.popBackStack()
    }
}