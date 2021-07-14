package com.example.ft_hangouts.presentation.navigation.router

import androidx.navigation.NavController
import com.example.ft_hangouts.presentation.navigation.FromContactEditor

class ContactEditorRouter(navController: NavController) :
    BaseRouter<FromContactEditor>(navController) {

    override fun goToScreen(destination: FromContactEditor) =
        when (destination) {
            is FromContactEditor.PreviousScreen -> goToPrevious()
            is FromContactEditor.ContactDetail -> navController.navigate(destination.navigateToId, destination.contact)
            else -> {}
        }
}