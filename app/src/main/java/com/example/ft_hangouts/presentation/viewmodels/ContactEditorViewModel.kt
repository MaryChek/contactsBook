package com.example.ft_hangouts.presentation.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import com.example.ft_hangouts.R
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.mappers.ContactMapper
import com.example.ft_hangouts.domain.models.Contact as DomainContact
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.models.ContactEditorState
import com.example.ft_hangouts.presentation.navigation.FromContactEditor
import com.example.ft_hangouts.presentation.viewmodels.base.BaseContactEditorViewModel
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class ContactEditorViewModel(
    private val interactor: ContactsInteractor,
    private val mapper: ContactMapper,
) : BaseContactEditorViewModel<ContactEditorState, FromContactEditor>(ContactEditorState()) {

    private val logTag: String = this::class.java.simpleName

    private fun updateModel(
        contactId: String? = model.contact?.id,
        personName: String? = model.contact?.name,
        personNumber: String? = model.contact?.number,
        isNumberIndividual: Boolean? = model.isNumberIndividual,
    ) {
        if (contactId != null) {
            val contact = Contact(contactId, personName, personNumber)
            model = ContactEditorState(contact, isNumberIndividual)
        } else {
            Log.e(logTag, "missing contact id", IllegalArgumentException())
        }
    }

    fun init(contact: Contact) {
        updateModel(
            contactId = contact.id,
            personName = contact.name,
            personNumber = contact.number
        )
        updateScreen()
    }

    override fun onContactCorrect() {
        val contact: Contact? = model.contact
        if (contact != null) {
            if (removeContact(contact)) {
                addNewContact(contact)
                goToUpdatedDetailContact(contact)
            }
        } else {
            Log.e(logTag, "missing contact", IllegalArgumentException())
        }
    }

    private fun removeContact(contact: Contact): Boolean {
        val success: Boolean =
            contact.id?.let { contactId ->
                interactor.removeContactById(contactId)
            } ?: false

        if (!success) {
            Log.e(logTag, "remove contact is fail", IllegalStateException())
        }
        return success
    }

    private fun addNewContact(contact: Contact) {
        val newContact: DomainContact = mapper.mapContact(contact)
        interactor.addContact(newContact)
    }

    private fun goToUpdatedDetailContact(contact: Contact) =
        goToScreen(
            FromContactEditor.Navigate.ContactDetail(
                R.id.open_ContactDetailsFragment_from_ContactEditorFragment,
                getBundleForContactModel(contact)
            )
        )

    override fun onAddPhotoClick() {

    }

    override fun onNameSubmit(name: String) =
        updateModel(personName = name)

    override fun onNumberSubmit(number: String) {
        val isNumberIndividual = interactor.isNumberIndividual(number, unlessId = model.contact?.id)
        updateModel(personNumber = number, isNumberIndividual = isNumberIndividual)
    }

    override fun showMessageError() =
        goToScreen(FromContactEditor.Command.ShowErrorMessage(model.errorMessageResId))

    override fun goToPrevious() =
        goToScreen(FromContactEditor.Navigate.PreviousScreen)

    private fun getBundleForContactModel(contact: Contact): Bundle =
        bundleOf(Contact::class.java.simpleName to contact)
}