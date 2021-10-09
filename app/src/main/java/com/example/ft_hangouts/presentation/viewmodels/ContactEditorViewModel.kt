package com.example.ft_hangouts.presentation.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import com.example.ft_hangouts.R
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.mappers.ContactMapper
import com.example.ft_hangouts.domain.models.Contact as DomainContact
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.navigation.FromContactEditor
import com.example.ft_hangouts.presentation.viewmodels.base.BaseContactEditorViewModel
import java.lang.IllegalArgumentException

class ContactEditorViewModel(
    interactor: ContactsInteractor,
    private val mapper: ContactMapper,
) : BaseContactEditorViewModel<FromContactEditor>(interactor) {

    override val logTag: String = this::class.java.simpleName

    fun initContact(contact: Contact) {
        updateModel(
            contactId = contact.id,
            personName = contact.name,
            personLastName = contact.lastName,
            personNumber = contact.number,
            personEmail = contact.email,
            imagePath = contact.imagePath,
        )
        updateScreen()
    }

    override fun onContactCorrect() {
        val contact: Contact? = model.contact
        if (contact != null) {
            updateContact(contact)
            goToUpdatedDetailContact(contact)
        } else {
            Log.e(logTag, "missing contact", IllegalArgumentException())
        }
    }

    private fun updateContact(contact: Contact) {
        val newContact: DomainContact = mapper.mapContact(contact)
        interactor.updateContact(newContact)
    }

    private fun goToUpdatedDetailContact(contact: Contact) =
        goToScreen(
            FromContactEditor.Navigate.ContactDetail(
                R.id.open_ContactDetailsFragment_from_ContactEditorFragment,
                getBundleForContactModel(contact)
            )
        )

    override fun onAddPhotoClick() {}

    override fun isNumberIndividual(number: String): Boolean =
        interactor.isNumberIndividual(number, unlessId = model.contact?.id)

    override fun showMessageError() =
        goToScreen(FromContactEditor.Command.ShowErrorMessage(model.errorMessageResId))

    override fun goToPrevious() =
        goToScreen(FromContactEditor.Navigate.PreviousScreen)

    private fun getBundleForContactModel(contact: Contact): Bundle =
        bundleOf(Contact::class.java.simpleName to contact)
}