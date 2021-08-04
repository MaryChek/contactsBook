package com.example.ft_hangouts.presentation.viewmodels

import android.util.Log
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.mappers.ContactMapper
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.models.ContactState
import com.example.ft_hangouts.presentation.navigation.FromContactCreator
import com.example.ft_hangouts.presentation.viewmodels.base.BaseContactEditorViewModel
import java.lang.IllegalArgumentException

class ContactCreatorViewModel(
    private val interactor: ContactsInteractor,
    private val mapper: ContactMapper,
) : BaseContactEditorViewModel<ContactState, FromContactCreator>(ContactState()) {

    private val logTag: String = this::class.java.simpleName

    fun init() {
        val contactId: String = interactor.getNewContactIndex()
        updateModel(contactId)
    }

    private fun updateModel(
        contactId: String? = model.contact?.id,
        personName: String? = model.contact?.name,
        personNumber: String? = model.contact?.number,
        isNumberIndividual: Boolean? = model.isNumberIndividual,
    ) {
        if (contactId != null) {
            val contact = Contact(contactId, personName, personNumber)
            model = ContactState(contact, isNumberIndividual)
        } else {
            Log.e(logTag, "missing contact id", IllegalArgumentException())
        }
    }

    override fun onContactCorrect() {
        model.contact?.let { contact ->
            interactor.addContact(mapper.mapContact(contact))
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
        goToScreen(FromContactCreator.Navigate.PreviousScreen)

    override fun showMessageError() =
        goToScreen(FromContactCreator.Command.ShowErrorMessage(model.errorMessageResId))
}