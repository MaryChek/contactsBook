package com.example.ft_hangouts.presentation.viewmodels

import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.ft_hangouts.R
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.navigation.FromContactsBook
import com.example.ft_hangouts.presentation.viewmodels.base.BaseViewModel

class ContactsBookViewModel(private val interactor: ContactsInteractor)
    : BaseViewModel<List<Contact>, FromContactsBook>(listOf()) {

    fun init() {
        val contacts = interactor.getAllContacts() // TODO add mapper
        updateModel(contacts.map {
            Contact(it.name, it.number)
        })
    }

    private fun updateModel(contacts: List<Contact>) {
        model = contacts
        updateScreen()
    }

    fun onButtonCreateNewContactClick() =
        goToScreen(
            FromContactsBook.ContactCreator(
                R.id.open_ContactCreatorFragment
            )
        )

    fun onIconChatClick(contact: Contact) {}
//        goToScreen(
//            FromContactsBook.Chat(
//                R.id.open_ContactChatFragment,
//                getBundleForContactModel(contact)
//            )
//        )

    fun onImgContactClick(contact: Contact) =
        goToScreen(
            FromContactsBook.ContactDetails(
                R.id.open_ContactDetailsFragment,
                getBundleForContactModel(contact)
            )
        )

    override fun goToPrevious() =
        goToScreen(FromContactsBook.PreviousScreen)

    private fun getBundleForContactModel(contact: Contact) : Bundle =
        bundleOf(Contact::class.java.simpleName to contact)
}