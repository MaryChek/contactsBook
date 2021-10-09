package com.example.ft_hangouts.presentation.viewmodels

import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.ft_hangouts.R
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.mappers.ContactMapper
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.navigation.FromContactsBook
import com.example.ft_hangouts.presentation.viewmodels.base.BaseViewModel

class ContactsBookViewModel(
    override val interactor: ContactsInteractor,
    private val mapper: ContactMapper,
) : BaseViewModel<List<Contact>, FromContactsBook>(interactor, listOf()) {

    var hasPermissionToGetSms: Boolean = false

    override fun init() {
        super.init()
        val contacts = interactor.getAllContacts()
        val currentContacts: List<Contact> = mapper.mapContacts(contacts)
        updateModel(currentContacts)
        goToScreen(FromContactsBook.Command.AccessGetSmsPermissions)
    }

    private fun updateModel(contacts: List<Contact>) {
        model = contacts
        updateScreen()
    }

    fun onButtonCreateNewContactClick() =
        goToScreen(
            FromContactsBook.Navigate.ContactCreator(
                R.id.open_ContactCreatorFragment
            )
        )

    fun onIconChatClick(contact: Contact) =
        goToScreen(
            FromContactsBook.Navigate.Chat(
                R.id.open_ContactChatFragment,
                getBundleForContactModel(contact)
            )
        )

    fun onImgContactClick(contact: Contact) =
        goToScreen(
            FromContactsBook.Navigate.ContactDetails(
                R.id.open_ContactDetailsFragment_from_ContactBook,
                getBundleForContactModel(contact)
            )
        )

    fun onGetSmsPermissionResponse(isGranted: Boolean) {
        hasPermissionToGetSms = isGranted
    }

    override fun goToPrevious() =
        goToScreen(FromContactsBook.Command.CloseActivity)

    private fun getBundleForContactModel(contact: Contact): Bundle =
        bundleOf(Contact::class.java.simpleName to contact)
}