package com.example.ft_hangouts.presentation.navigation

import android.os.Bundle
import androidx.annotation.IdRes

sealed class FromContactsBook : BaseNavigation {
    class ContactDetails(@IdRes val navigateToId: Int, val contact: Bundle?) : FromContactsBook()

    class Chat(@IdRes val navigateToId: Int, val contact: Bundle?) : FromContactsBook()

    class ContactCreator(@IdRes val navigateToId: Int) : FromContactsBook()

    object PreviousScreen: FromContactsBook()
}