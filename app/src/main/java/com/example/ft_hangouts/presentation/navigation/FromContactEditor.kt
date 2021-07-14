package com.example.ft_hangouts.presentation.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation

sealed class FromContactEditor : BaseNavigation {
    class PreviousScreen(@IdRes val navigateToId: Int) : FromContactEditor()

    class ShowErrorMessage(@StringRes val errorMessageResId: Int): FromContactEditor()

    class ContactDetail(@IdRes val navigateToId: Int, val contact: Bundle): FromContactEditor()
}