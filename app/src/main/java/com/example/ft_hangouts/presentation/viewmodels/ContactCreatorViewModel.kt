package com.example.ft_hangouts.presentation.viewmodels

import com.example.ft_hangouts.domain.interactors.ColorInteractor
import com.example.ft_hangouts.domain.interactors.ContactInteractor
import com.example.ft_hangouts.presentation.mappers.ContactMapper
import com.example.ft_hangouts.presentation.navigation.FromContactCreator
import com.example.ft_hangouts.presentation.viewmodels.base.BaseContactEditorViewModel

class ContactCreatorViewModel(
    colorInteractor: ColorInteractor,
    interactor: ContactInteractor,
    private val mapper: ContactMapper,
) : BaseContactEditorViewModel<FromContactCreator>(colorInteractor, interactor) {

    override val logTag: String = this::class.java.simpleName

    override fun onViewCreated() {
        super.onViewCreated()
        val contactId: String = interactor.getNewContactIndex()
        updateModel(contactId)
    }

    override fun onContactCorrect() =
        saveContact()

    private fun saveContact() {
        model.contact?.let { contact ->
            interactor.addContact(mapper.mapContact(contact))
        }
        goToPrevious()
    }

    override fun isNumberIndividual(number: String): Boolean =
        interactor.isNumberIndividual(number)

    override fun goToPrevious() =
        updateAction(FromContactCreator.Navigate.PreviousScreen)

    override fun showMessageError() =
        updateAction(FromContactCreator.Command.ShowErrorMessage(model.errorMessageResId))
}