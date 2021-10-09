package com.example.ft_hangouts.presentation.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import com.example.ft_hangouts.R
import com.example.ft_hangouts.domain.interactors.ColorInteractor
import com.example.ft_hangouts.domain.interactors.ContactInteractor
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.models.ContactDetailState
import com.example.ft_hangouts.presentation.navigation.FromContactDetails
import com.example.ft_hangouts.presentation.viewmodels.base.BaseViewModel

class ContactDetailsViewModel(
    colorInteractor: ColorInteractor,
    private val interactor: ContactInteractor,
) : BaseViewModel<ContactDetailState, FromContactDetails>(colorInteractor, ContactDetailState(Contact())) {

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
        updateAction(FromContactDetails.Navigate.PreviousScreen)

    fun onEditContactClick() =
        updateAction(
            FromContactDetails.Navigate.ContactEditor(
                R.id.open_ContactEditorFragment,
                getBundleForContactModel(model)
            )
        )

    fun onIconChatClick() =
        updateAction(
            FromContactDetails.Navigate.Chat(
                R.id.open_ContactChatFragment,
                getBundleForContactModel(model)
            )
        )

    fun onDeleteContactClick() =
        model.id?.let { contactId ->
            updateAction(FromContactDetails.Command.OpenDeleteContactDialog(contactId))
        } ?: Log.e(logTag, "missing contact id")

    fun onCallClick() =
        updateAction(FromContactDetails.Command.AccessCallPhonePermissions)

    fun onContactDeletionConfirmed(contactId: String) {
        interactor.removeContactById(contactId)
        goToPrevious()
    }

    fun onCallPhonePermissionResponse(isGranted: Boolean) {
        if (isGranted) {
            model.number?.let { number ->
                updateAction(FromContactDetails.Command.CallPhone(number))
            }
        }
    }

    private fun getBundleForContactModel(contact: Contact): Bundle =
        bundleOf(Contact::class.java.simpleName to contact)
}