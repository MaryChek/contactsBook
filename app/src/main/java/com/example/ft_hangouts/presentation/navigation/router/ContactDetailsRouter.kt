package com.example.ft_hangouts.presentation.navigation.router

import androidx.navigation.NavController
import com.example.ft_hangouts.presentation.navigation.FromContactDetails

class ContactDetailsRouter(navController: NavController) :
    BaseRouter<FromContactDetails>(navController) {

    override fun goToScreen(destination: FromContactDetails) =
        when (destination) {
            is FromContactDetails.ContactEditor ->
                navController.navigate(destination.navigateToId, destination.contact)
            is FromContactDetails.PreviousScreen -> goToPrevious()
        }
}