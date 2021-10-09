package com.example.ft_hangouts.data.repository

import com.example.ft_hangouts.domain.models.Contact

interface ContactRepository {
    fun getAllContacts(): List<Contact>

    fun addContact(contact: Contact): Long

    fun removeContactById(contactId: String)

    fun updateContact(contact: Contact)
}