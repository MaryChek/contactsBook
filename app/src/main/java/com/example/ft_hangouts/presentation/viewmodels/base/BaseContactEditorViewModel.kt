package com.example.ft_hangouts.presentation.viewmodels.base

import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.domain.models.Contact
import com.example.ft_hangouts.presentation.models.ContactState
import com.example.ft_hangouts.presentation.navigation.BaseNavigation

abstract class BaseContactEditorViewModel<FromScreen : BaseNavigation>(
    protected val interactor: ContactsInteractor
) : BaseViewModel<ContactState, FromScreen>(ContactState()) {

    protected fun updateModel(
        personName: String? = model.contact?.name,
        personNumber: String? = model.contact?.number,
        isNumberIndividual: Boolean? = model.isNumberIndividual,
    ) {
        model = ContactState(
            contact = Contact(personName, personNumber), isNumberIndividual = isNumberIndividual
        )
    }

    fun onSaveContactClick() {
        if (model.isCreateSuccess) {
            model.contact?.let { contact ->
                interactor.addContact(contact)
            }
            goToPrevious()
        } else {
            showMessageError()
        }
    }

    abstract fun onAddPhotoClick()

    open fun onNameSubmit(name: String) =
        updateModel(personName = name)

    open fun onNumberSubmit(number: String) {
        val isNumberIndividual = interactor.isNumberIndividual(number)
        updateModel(personNumber = number, isNumberIndividual = isNumberIndividual)
    }

    abstract fun showMessageError()
}