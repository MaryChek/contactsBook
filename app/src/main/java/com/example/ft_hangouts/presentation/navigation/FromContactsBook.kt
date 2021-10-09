package com.example.ft_hangouts.presentation.navigation

import android.os.Bundle
import com.example.ft_hangouts.domain.models.ColorState
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation

sealed class FromContactsBook : BaseNavigation {

    sealed class Navigate : BaseNavigation.Navigate, FromContactsBook() {
        class ContactDetails(val navigateToId: Int, val contact: Bundle?) : Navigate()

        class Chat(val navigateToId: Int, val contact: Bundle?) : Navigate()

        class ContactCreator(val navigateToId: Int) : Navigate()
    }

    sealed class Command : BaseNavigation.Command, FromContactsBook() {
        object CloseActivity : Command()
        object AccessGetSmsPermissions : Command()
        object AccessCallPhonePermissions : Command()
        class CallPhone(val number: String) : Command()
    }
}