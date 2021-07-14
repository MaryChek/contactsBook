package com.example.ft_hangouts.presentation.navigation.router

import androidx.navigation.NavController
import com.example.ft_hangouts.presentation.navigation.FromContactCreator

class ContactCreatorRouter(navController: NavController)
    : BaseRouter<FromContactCreator>(navController) {

    override fun goToScreen(destination: FromContactCreator) =
        when (destination) {
            is FromContactCreator.ContactsBook -> navController.navigate(destination.navigateToId)
            is FromContactCreator.PreviousScreen -> goToPrevious()
            else -> {}
        }
}