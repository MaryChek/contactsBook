package com.example.ft_hangouts.presentation.fragments

import com.example.ft_hangouts.presentation.fragments.base.BaseContactEditorFragment
import com.example.ft_hangouts.presentation.navigation.FromContactCreator
import com.example.ft_hangouts.presentation.navigation.router.ContactCreatorRouter
import com.example.ft_hangouts.presentation.viewmodels.ContactCreatorViewModel

class ContactCreatorFragment : BaseContactEditorFragment<
        FromContactCreator, FromContactCreator.Navigate,
        ContactCreatorRouter, ContactCreatorViewModel>() {

    override val logTag: String = this::class.java.simpleName

    override fun getViewModelClass(): Class<ContactCreatorViewModel> =
        ContactCreatorViewModel::class.java

    override fun getNavRouter(): ContactCreatorRouter =
        ContactCreatorRouter(navController)

    override fun navigateTo(destination: FromContactCreator) =
        when (destination) {
            is FromContactCreator.Command.ShowErrorMessage ->
                showErrorMessage(destination.errorMessageResId)
            is FromContactCreator.Navigate ->
                router.goToScreen(destination)
        }
}