package com.example.ft_hangouts.presentation.viewmodels

import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.ft_hangouts.R
import com.example.ft_hangouts.domain.interactors.ColorInteractor
import com.example.ft_hangouts.domain.interactors.ContactInteractor
import com.example.ft_hangouts.presentation.mappers.ContactMapper
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.navigation.FromContactsBook
import com.example.ft_hangouts.presentation.viewmodels.base.BaseViewModel

class ContactsBookViewModel(
    colorInteractor: ColorInteractor,
    private val interactor: ContactInteractor,
    private val mapper: ContactMapper,
) : BaseViewModel<List<Contact>, FromContactsBook>(colorInteractor, listOf()) {

    private var callPhone: String? = null

    override fun onViewCreated() {
        super.onViewCreated()
        setOnNewContactCreatedListener()
        fetchContacts()
        updateAction(FromContactsBook.Command.AccessGetSmsPermissions)
    }

    private fun setOnNewContactCreatedListener() =
        interactor.setOnNewContactCreatedListener {
            fetchContacts()
        }

    private fun fetchContacts() {
        val contacts = interactor.getAllContacts()
        val currentContacts: List<Contact> = mapper.mapContacts(contacts)
        updateModel(currentContacts)
    }

    private fun updateModel(contacts: List<Contact>) {
        model = contacts
        updateScreen()
    }

    fun onButtonCreateNewContactClick() =
        updateAction(
            FromContactsBook.Navigate.ContactCreator(
                R.id.open_ContactCreatorFragment
            )
        )

    fun onIconChatClick(contact: Contact) =
        updateAction(
            FromContactsBook.Navigate.Chat(
                R.id.open_ContactChatFragment,
                getBundleForContactModel(contact)
            )
        )

    fun onImgContactClick(contact: Contact) =
        updateAction(
            FromContactsBook.Navigate.ContactDetails(
                R.id.open_ContactDetailsFragment_from_ContactBook,
                getBundleForContactModel(contact)
            )
        )

    fun onCallClick(contact: Contact) {
        callPhone = contact.number
        updateAction(FromContactsBook.Command.AccessCallPhonePermissions)
    }

    fun onCallPhonePermissionResponse(isGranted: Boolean) {
        if (isGranted) {
            callPhone?.let { number ->
                updateAction(FromContactsBook.Command.CallPhone(number))
            }
        }
    }

    override fun goToPrevious() =
        updateAction(FromContactsBook.Command.CloseActivity)

    private fun getBundleForContactModel(contact: Contact): Bundle =
        bundleOf(Contact::class.java.simpleName to contact)
}