package com.example.ft_hangouts.presentation.navigation

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation

sealed class FromContactCreator: BaseNavigation {

    sealed class Navigate : BaseNavigation.Navigate, FromContactCreator() {
        class ContactsBook(@IdRes val navigateToId: Int) : Navigate()

        object PreviousScreen: Navigate()
    }

    sealed class Command : BaseNavigation.Command, FromContactCreator() {
        class ShowErrorMessage(@StringRes val errorMessageResId: Int): Command()
    }
}
