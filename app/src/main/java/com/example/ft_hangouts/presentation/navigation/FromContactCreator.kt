package com.example.ft_hangouts.presentation.navigation

import androidx.annotation.IdRes
import androidx.annotation.StringRes

sealed class FromContactCreator: BaseNavigation {
    class ContactsBook(@IdRes val navigateToId: Int): FromContactCreator()

    class ShowErrorMessage(@StringRes val errorMessageResId: Int): FromContactCreator()

    object PreviousScreen: FromContactCreator()
}
