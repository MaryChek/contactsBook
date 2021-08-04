package com.example.ft_hangouts.presentation.navigation.router

import androidx.navigation.NavController
import com.example.ft_hangouts.presentation.navigation.FromContactsBook

class ContactsBookRouter(navController: NavController)
    : BaseRouter<FromContactsBook.Navigate>(navController) {

    override fun goToScreen(destination: FromContactsBook.Navigate) =
        when (destination) {
            is FromContactsBook.Navigate.ContactDetails ->
                navController.navigate(destination.navigateToId, destination.contact)
            is FromContactsBook.Navigate.Chat ->
                navController.navigate(destination.navigateToId, destination.contact)
            is FromContactsBook.Navigate.ContactCreator ->
                navController.navigate(destination.navigateToId)
        }
}