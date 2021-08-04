package com.example.ft_hangouts.presentation.fragments

import android.os.Bundle
import android.view.View
import com.example.ft_hangouts.presentation.fragments.base.BaseContactEditorFragment
import com.example.ft_hangouts.presentation.models.ContactState
import com.example.ft_hangouts.presentation.navigation.FromContactCreator
import com.example.ft_hangouts.presentation.navigation.router.ContactCreatorRouter
import com.example.ft_hangouts.presentation.viewmodels.ContactCreatorViewModel

class ContactCreatorFragment : BaseContactEditorFragment<
        ContactState, FromContactCreator, FromContactCreator.Navigate,
        ContactCreatorRouter, ContactCreatorViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
    }

    override fun getViewModelClass(): Class<ContactCreatorViewModel> =
        ContactCreatorViewModel::class.java

    override fun getNavRouter(): ContactCreatorRouter =
        ContactCreatorRouter(navController)

    override fun goToScreen(destination: FromContactCreator) =
        when (destination) {
            is FromContactCreator.Command.ShowErrorMessage ->
                showErrorMessage(destination.errorMessageResId)
            is FromContactCreator.Navigate ->
                router.goToScreen(destination)
        }
}