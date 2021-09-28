package com.example.ft_hangouts.presentation.navigation

import androidx.annotation.StringRes
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation

sealed class FromContactChat : BaseNavigation {

    sealed class Navigate : BaseNavigation.Navigate, FromContactChat() {
        object PreviousScreen : Navigate()
    }

    sealed class Command : BaseNavigation.Command, FromContactChat() {
        object ClearEditTextAndSetEditorAction : Command()
        object AccessSendSmsPermissions : Command()
        object AccessGetSmsPermissions : Command()//TODO move to baseFragment
        class ShowErrorMessage(@StringRes val errorMessage: Int) : Command()
    }
}