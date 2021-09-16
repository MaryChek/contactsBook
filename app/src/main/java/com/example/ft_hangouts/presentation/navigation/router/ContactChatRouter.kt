package com.example.ft_hangouts.presentation.navigation.router

import androidx.navigation.NavController
import com.example.ft_hangouts.presentation.navigation.FromContactChat

class ContactChatRouter(navController: NavController) :
    BaseRouter<FromContactChat.Navigate>(navController) {

    override fun goToScreen(destination: FromContactChat.Navigate) =
        when (destination) {
            is FromContactChat.Navigate.PreviousScreen -> {
                goToPrevious()
            }
        }
}