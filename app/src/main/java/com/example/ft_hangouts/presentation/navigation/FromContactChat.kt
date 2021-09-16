package com.example.ft_hangouts.presentation.navigation

import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation

sealed class FromContactChat: BaseNavigation {

    sealed class Navigate : BaseNavigation.Navigate, FromContactChat() {
        object PreviousScreen: Navigate()
    }
}