package com.example.ft_hangouts.presentation.navigation.router

import androidx.navigation.NavController
import com.example.ft_hangouts.presentation.navigation.FromContactCreator

class ContactCreatorRouter(navController: NavController) :
    BaseRouter<FromContactCreator.Navigate>(navController) {

    override fun goToScreen(destination: FromContactCreator.Navigate) =
        when (destination) {
            is FromContactCreator.Navigate.ContactsBook ->
                navController.navigate(destination.navigateToId)
            is FromContactCreator.Navigate.PreviousScreen -> {
                goToPrevious()
            }
        }
}