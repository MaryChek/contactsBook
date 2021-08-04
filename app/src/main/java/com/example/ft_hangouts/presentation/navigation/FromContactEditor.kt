package com.example.ft_hangouts.presentation.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation

sealed class FromContactEditor : BaseNavigation {

    sealed class Navigate : BaseNavigation.Navigate, FromContactEditor() {
        object PreviousScreen : Navigate()

        class ContactDetail(@IdRes val navigateToId: Int, val contact: Bundle) : Navigate()
    }

    sealed class Command : BaseNavigation.Command, FromContactEditor() {
        class ShowErrorMessage(@StringRes val errorMessageResId: Int) : Command()
    }
}