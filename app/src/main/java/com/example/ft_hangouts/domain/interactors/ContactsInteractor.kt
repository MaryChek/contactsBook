package com.example.ft_hangouts.domain.interactors

import android.util.Log
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.domain.models.Contact
import java.lang.IllegalStateException
import java.lang.NumberFormatException

class ContactsInteractor(private val repository: ContactRepository) {

    private val logTag: String = this::class.java.simpleName

    fun getAllContacts(): List<Contact> =
        repository.getAllContacts()

    fun getNewContactIndex(): String =
        getAllContacts().size.toString()

    fun isNumberIndividual(number: String, unlessId: String? = null): Boolean {
        val contactList = getAllContacts()
        return contactList.find { contact ->
            contact.number == number && contact.id != unlessId
        } == null
    }

    fun addContact(contact: Contact) =
        repository.addContact(contact)

    fun removeContactById(contactId: String): Boolean =
        try {
            repository.removeContact(contactId.toInt())
            true
        } catch (e: NumberFormatException) {
            Log.e(logTag, "contactId is invalid", IllegalStateException())
            false
        }
}