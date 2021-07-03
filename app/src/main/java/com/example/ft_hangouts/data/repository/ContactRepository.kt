package com.example.ft_hangouts.data.repository

import com.example.ft_hangouts.domain.models.Contact

class ContactRepository {
    private val contactList: MutableList<Contact> = mutableListOf()

    fun getAllContacts(): List<Contact> =
        contactList

    fun addContact(contact: Contact) =
        contactList.add(contact)

    fun removeContact(contact: Contact) =
        contactList.remove(contact)

    fun updateContactById(contact: Contact) {

    }

}