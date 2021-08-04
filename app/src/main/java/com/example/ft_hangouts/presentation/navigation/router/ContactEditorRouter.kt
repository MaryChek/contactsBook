package com.example.ft_hangouts.presentation.navigation.router

import androidx.navigation.NavController
import com.example.ft_hangouts.presentation.navigation.FromContactEditor

class ContactEditorRouter(navController: NavController) :
    BaseRouter<FromContactEditor.Navigate>(navController) {

    override fun goToScreen(destination: FromContactEditor.Navigate) =
        when (destination) {
            is FromContactEditor.Navigate.PreviousScreen ->
                goToPrevious()
            is FromContactEditor.Navigate.ContactDetail ->
                navController.navigate(destination.navigateToId, destination.contact)
        }
}