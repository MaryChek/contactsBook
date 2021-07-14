package com.example.ft_hangouts.presentation.viewmodels

import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.models.ContactState
import com.example.ft_hangouts.presentation.navigation.FromContactCreator
import com.example.ft_hangouts.presentation.viewmodels.base.BaseContactEditorViewModel

class ContactCreatorViewModel(private val interactor: ContactsInteractor) :
    BaseContactEditorViewModel<ContactState, FromContactCreator>(ContactState()) {

    private fun updateModel(
        personName: String? = model.contact?.name,
        personNumber: String? = model.contact?.number,
        isNumberIndividual: Boolean? = model.isNumberIndividual,
    ) {
        model = ContactState(Contact(personName, personNumber), isNumberIndividual)
    }

    override fun onContactCorrect() {
        model.contact?.let { contact ->
            interactor.addContact(
                com.example.ft_hangouts.domain.models.Contact(
                    contact.name,
                    contact.number
                )
            ) // TODO add mapper
        }
        goToPrevious()
    }

    override fun onAddPhotoClick() {
        //TODO спросить доступ к галерее и поставить фото
    }

    override fun onNameSubmit(name: String) =
        updateModel(personName = name)

    override fun onNumberSubmit(number: String) {
        val isNumberIndividual = interactor.isNumberIndividual(number)
        updateModel(personNumber = number, isNumberIndividual = isNumberIndividual)
    }

    override fun goToPrevious() =
        goToScreen(FromContactCreator.PreviousScreen)

    override fun showMessageError() =
        goToScreen(FromContactCreator.ShowErrorMessage(model.errorMessageResId))
}