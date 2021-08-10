package com.example.ft_hangouts.data.room.database

import com.example.ft_hangouts.domain.models.Contact

interface DbDao {
    fun getAllContacts(): List<Contact>

    fun addContact(contact: Contact)

    fun removeContactById(contactId: String)

    fun updateContact(contact: Contact)
}