package com.example.ft_hangouts.presentation.navigation

interface GoToScreen<T: BaseNavigation> {
    fun goToScreen(destination: T): Any
}