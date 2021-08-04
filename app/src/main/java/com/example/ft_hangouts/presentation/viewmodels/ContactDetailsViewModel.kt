package com.example.ft_hangouts.presentation.viewmodels

import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.ft_hangouts.R
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.navigation.FromContactDetails
import com.example.ft_hangouts.presentation.viewmodels.base.BaseViewModel

class ContactDetailsViewModel
    : BaseViewModel<Contact, FromContactDetails>(Contact()) {

    fun init(contact: Contact) {
        updateModel(contact)
    }

    private fun updateModel(contact: Contact) {
        model = contact
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

    private fun getBundleForContactModel(contact: Contact): Bundle =
        bundleOf(Contact::class.java.simpleName to contact)
}