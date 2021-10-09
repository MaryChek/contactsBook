package com.example.ft_hangouts.presentation.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import com.example.ft_hangouts.R
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.models.ContactDetailState
import com.example.ft_hangouts.presentation.navigation.FromContactDetails
import com.example.ft_hangouts.presentation.navigation.FromContactsBook
import com.example.ft_hangouts.presentation.viewmodels.base.BaseViewModel

class ContactDetailsViewModel(override val interactor: ContactsInteractor)
    : BaseViewModel<ContactDetailState, FromContactDetails>(interactor, ContactDetailState(Contact())) {

    val logTag: String = ContactDetailsViewModel::class.java.simpleName

    fun initContact(contact: Contact) {
        val contactState = ContactDetailState(contact)
        updateModel(contactState)
    }

    private fun updateModel(contactState: ContactDetailState) {
        model = contactState
        updateScreen()
    }

    override fun goToPrevious() =
        goToScreen(FromContactDetails.Navigate.PreviousScreen)

    fun onEditContactClick() {
        goToScreen(
            FromContactDetails.Navigate.ContactEditor(
                R.id.open_ContactEditorFragment,
                getBundleForContactModel(model)
            )
        )
    }

    fun onIconChatClick() =
        goToScreen(
            FromContactDetails.Navigate.Chat(
                R.id.open_ContactChatFragment,
                getBundleForContactModel(model)
            )
        )

    fun onDeleteContactClick() =
        model.id?.let { contactId ->
            goToScreen(FromContactDetails.Command.OpenDeleteContactDialog(contactId))
        } ?: Log.e(logTag, "missing contact id")

    fun onContactDeletionConfirmed(contactId: String) {
        interactor.removeContactById(contactId)
        goToPrevious()
    }

    private fun getBundleForContactModel(contact: Contact): Bundle =
        bundleOf(Contact::class.java.simpleName to contact)
}