package com.example.ft_hangouts.domain.interactors

import android.util.Log
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.domain.models.Contact

class ContactInteractor(private val repository: ContactRepository) {

    private val logTag: String = this::class.java.simpleName
    private var onNewContactCreatedListener: ((Contact) -> Unit)? = null

    fun getAllContacts(): List<Contact> =
        repository.getAllContacts()

    fun addContact(contact: Contact): Long =
        repository.addContact(contact)

    fun removeContactById(contactId: String) =
        repository.removeContactById(contactId)

    fun updateContact(contact: Contact) =
        repository.updateContact(contact)

    fun getNewContactIndex(): String =
        getAllContacts().size.toString()

    fun isNumberIndividual(number: String, unlessId: String? = null): Boolean =
        getAllContacts().find { contact ->
            contact.numberEquals(number) && contact.id != unlessId
        } == null

    fun isNumberValid(number: String): Boolean =
        Contact(number = number).isValid()

    fun setOnNewContactCreatedListener(listener: (Contact) -> Unit) {
        onNewContactCreatedListener = listener
    }

    fun getContactIdByNumberOrCreateNew(number: String): String {
        val id: String = findContactIdByNumber(number) ?: createNewContact(number)
        if (id == ERROR_ID) {
            Log.e(logTag, "contact create fail")
        }
        return id
    }

    private fun findContactIdByNumber(number: String): String? =
        getAllContacts().find { contact ->
            contact.numberEquals(number)
        }?.id

    private fun createNewContact(number: String): String {
        val newId: String = getNewContactIndex()
        val contact = Contact(id = newId, name = number, number = number)
        val id: String = addContact(contact).toString()
        onNewContactCreated(contact)
        return id
    }

    private fun onNewContactCreated(contact: Contact) =
        onNewContactCreatedListener?.invoke(contact) ?: Log.e(logTag, "listener not init")

    companion object {
        private const val ERROR_ID = "-1"
    }
}