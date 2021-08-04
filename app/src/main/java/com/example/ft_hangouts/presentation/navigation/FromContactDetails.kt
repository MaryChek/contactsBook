package com.example.ft_hangouts.presentation.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation

sealed class FromContactDetails : BaseNavigation {

    sealed class Navigate : BaseNavigation.Navigate, FromContactDetails() {
        object PreviousScreen : Navigate()

        class ContactEditor(@IdRes val navigateToId: Int, val contact: Bundle?) : Navigate()
    }
}
