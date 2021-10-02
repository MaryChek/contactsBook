package com.example.ft_hangouts.presentation.navigation

import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation

sealed class FromMainActivity: BaseNavigation {
    sealed class Navigate: FromMainActivity() {
        class ShowToast(val time: Int) : Navigate()
    }
}
