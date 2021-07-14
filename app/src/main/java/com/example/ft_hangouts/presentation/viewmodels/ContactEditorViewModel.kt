package com.example.ft_hangouts.presentation.viewmodels

import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.ft_hangouts.R
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.models.ContactEditorState
import com.example.ft_hangouts.presentation.navigation.FromContactEditor
import com.example.ft_hangouts.presentation.viewmodels.base.BaseContactEditorViewModel

class ContactEditorViewModel(private val interactor: ContactsInteractor) :
    BaseContactEditorViewModel<ContactEditorState, FromContactEditor>(ContactEditorState()) {

    private fun updateModel(
        currentContact: Contact? = model.currentContact,
        personName: String? = model.contact?.name,
        personNumber: String? = model.contact?.number,
        isNumberIndividual: Boolean? = model.isNumberIndividual,
    ) {
        model = ContactEditorState(
            currentContact,
            Contact(personName, personNumber),
            isNumberIndividual
        )
    }

    fun init(contact: Contact) {
        updateModel(currentContact = contact)
        updateScreen()
    }

    override fun onContactCorrect() {
        model.currentContact?.let { contact ->
            interactor.removeContact(
                com.example.ft_hangouts.domain.models.Contact(
                    contact.name,
                    contact.number
                )
            ) //TODO add mapper
        }
        val contact: Contact? = model.contact
        if (contact != null) {
            interactor.addContact(
                com.example.ft_hangouts.domain.models.Contact(
                    contact.name,
                    contact.number
                )
            ) //TODO add mapper
            goToUpdatedDetailContact(contact)
        }
    }

    private fun goToUpdatedDetailContact(contact: Contact) =
        goToScreen(
            FromContactEditor.ContactDetail(
                R.id.ContactDetailsFragment,
                getBundleForContactModel(contact)
            )
        )

    override fun onAddPhotoClick() {

    }

    override fun onNameSubmit(name: String) =
        updateModel(personName = name)

    override fun onNumberSubmit(number: String) {
        val isNumberIndividual = interactor.isNumberIndividual(number)
        updateModel(personNumber = number, isNumberIndividual = isNumberIndividual)
    }

    override fun showMessageError() =
        goToScreen(FromContactEditor.ShowErrorMessage(model.errorMessageResId))

    override fun goToPrevious() =
        goToScreen(FromContactEditor.PreviousScreen(R.id.ContactDetailsFragment))

    private fun getBundleForContactModel(contact: Contact): Bundle =
        bundleOf(Contact::class.java.simpleName to contact)
}