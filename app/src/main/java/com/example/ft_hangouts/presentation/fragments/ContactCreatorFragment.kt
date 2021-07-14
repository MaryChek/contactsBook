package com.example.ft_hangouts.presentation.fragments

import com.example.ft_hangouts.presentation.fragments.base.BaseContactEditorFragment
import com.example.ft_hangouts.presentation.models.ContactState
import com.example.ft_hangouts.presentation.navigation.FromContactCreator
import com.example.ft_hangouts.presentation.navigation.router.ContactCreatorRouter
import com.example.ft_hangouts.presentation.viewmodels.ContactCreatorViewModel

class ContactCreatorFragment
    : BaseContactEditorFragment<ContactState, FromContactCreator, ContactCreatorRouter, ContactCreatorViewModel>() {

    override fun getViewModelClass(): Class<ContactCreatorViewModel> =
        ContactCreatorViewModel::class.java

    override fun getNavRouter(): ContactCreatorRouter =
        ContactCreatorRouter(navController)

    override fun goToScreen(destination: FromContactCreator) =
        when (destination) {
            is FromContactCreator.ShowErrorMessage -> showErrorMessage(destination.errorMessageResId)
            else -> router.goToScreen(destination)
        }
}