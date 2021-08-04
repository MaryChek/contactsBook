package com.example.ft_hangouts.presentation.navigation.base

interface GoToScreen<T: BaseNavigation.Navigate> {
    fun goToScreen(destination: T): Any
}