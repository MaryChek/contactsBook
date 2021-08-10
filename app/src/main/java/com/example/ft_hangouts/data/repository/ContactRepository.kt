package com.example.ft_hangouts.data.repository

import com.example.ft_hangouts.data.room.database.DBContacts
import com.example.ft_hangouts.domain.models.Contact

class ContactRepository(private val dbContacts: DBContacts) {

    fun getAllContacts(): List<Contact> =
        dbContacts.getAllContacts()

    fun addContact(contact: Contact) =
        dbContacts.addContact(contact)

    fun removeContact(contactId: String) =
        dbContacts.removeContactById(contactId)

    fun updateContactById(contact: Contact) =
        dbContacts.updateContact(contact)
}