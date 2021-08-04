package com.example.ft_hangouts.presentation.navigation.router

import androidx.navigation.NavController
import com.example.ft_hangouts.presentation.navigation.FromContactDetails

class ContactDetailsRouter(navController: NavController) :
    BaseRouter<FromContactDetails.Navigate>(navController) {

    override fun goToScreen(destination: FromContactDetails.Navigate) =
        when (destination) {
            is FromContactDetails.Navigate.ContactEditor ->
                navController.navigate(destination.navigateToId, destination.contact)
            is FromContactDetails.Navigate.PreviousScreen ->
                goToPrevious()
        }
}