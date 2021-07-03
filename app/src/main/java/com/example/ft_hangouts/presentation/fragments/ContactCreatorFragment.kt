package com.example.ft_hangouts.presentation.fragments

import com.example.ft_hangouts.presentation.fragments.base.BaseContactEditorFragment
import com.example.ft_hangouts.presentation.navigation.FromContactCreator
import com.example.ft_hangouts.presentation.viewmodels.ContactCreatorViewModel

class ContactCreatorFragment
    : BaseContactEditorFragment<FromContactCreator, ContactCreatorViewModel>() {

    override fun getViewModelClass(): Class<ContactCreatorViewModel> =
        ContactCreatorViewModel::class.java

    override fun goToScreen(destination: FromContactCreator): Any =
        when (destination) {
            is FromContactCreator.ContactsBook -> navigate(destination.navigateToId)
            is FromContactCreator.ShowErrorMessage -> showErrorMessage(destination.errorMessageResId)
            is FromContactCreator.PreviousScreen -> navigateToPrevious()
        }
}