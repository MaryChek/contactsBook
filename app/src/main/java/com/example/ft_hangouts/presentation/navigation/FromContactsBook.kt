package com.example.ft_hangouts.presentation.navigation

import android.os.Bundle
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation

sealed class FromContactsBook : BaseNavigation {
    class ContactDetails(val navigateToId: Int, val contact: Bundle?) : FromContactsBook()

    class Chat(val navigateToId: Int, val contact: Bundle?) : FromContactsBook()

    class ContactCreator(val navigateToId: Int) : FromContactsBook()

    object PreviousScreen: FromContactsBook()
}