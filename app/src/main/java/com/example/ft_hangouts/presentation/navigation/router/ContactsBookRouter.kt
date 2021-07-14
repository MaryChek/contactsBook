package com.example.ft_hangouts.presentation.navigation.router

import androidx.navigation.NavController
import com.example.ft_hangouts.presentation.navigation.FromContactsBook

class ContactsBookRouter(navController: NavController)
    : BaseRouter<FromContactsBook>(navController) {

    override fun goToScreen(destination: FromContactsBook) =
        when (destination) {
            is FromContactsBook.ContactDetails ->
                navController.navigate(destination.navigateToId, destination.contact)
            is FromContactsBook.Chat ->
                navController.navigate(destination.navigateToId, destination.contact)
            is FromContactsBook.ContactCreator ->
                navController.navigate(destination.navigateToId)
            else -> {}
        }
}