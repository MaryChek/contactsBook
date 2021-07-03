package com.example.ft_hangouts.presentation.viewmodels

import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.navigation.FromContactEditor
import com.example.ft_hangouts.presentation.viewmodels.base.BaseContactEditorViewModel

class ContactEditorViewModel(interactor: ContactsInteractor)
    : BaseContactEditorViewModel<FromContactEditor>(interactor) {

    fun init(contact: Contact) {
        updateModel(personName = contact.name, personNumber = contact.number)
    }

    override fun onAddPhotoClick() {
        TODO("Not yet implemented")
    }

    override fun showMessageError() {
        TODO("Not yet implemented")
    }

    override fun goToPrevious() {
        TODO("Not yet implemented")
    }
}