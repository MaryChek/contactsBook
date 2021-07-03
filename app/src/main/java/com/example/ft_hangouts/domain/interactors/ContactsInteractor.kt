package com.example.ft_hangouts.domain.interactors

import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.domain.models.Contact

class ContactsInteractor(private val repository: ContactRepository) {
    fun getAllContacts(): List<Contact> =
        repository.getAllContacts()

    fun isNumberIndividual(number: String): Boolean {
        val contactList = getAllContacts()
        return contactList.find { contact ->
            contact.number == number
        } == null
    }

    fun addContact(contact: Contact) =
        repository.addContact(contact)

    fun removeContact(contact: Contact) =
        repository.removeContact(contact)
}