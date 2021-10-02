package com.example.ft_hangouts.domain.interactors

import android.telephony.SmsManager
import android.util.Log
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.domain.mappers.MessageMapper
import com.example.ft_hangouts.domain.models.ChatMessage
import com.example.ft_hangouts.domain.models.Contact
import com.example.ft_hangouts.presentation.receiver.model.Sms

class ContactsInteractor(
    private val repository: ContactRepository,
    private val mapper: MessageMapper
) : GetMessage {

    private val logTag: String = this::class.java.simpleName
    private var onNewMessageCreatedListener: ((ChatMessage) -> Unit)? = null

    fun getAllContacts(): List<Contact> =
        repository.getAllContacts()

    fun getNewContactIndex(): String =
        getAllContacts().size.toString()

    fun isNumberIndividual(number: String, unlessId: String? = null): Boolean =
        getAllContacts().find { contact ->
            contact.number == number && contact.id != unlessId
        } == null

    private fun findContactIdByNumber(number: String): String? =
        getAllContacts().find { contact ->
            contact.number == number
        }?.id

    fun addContact(contact: Contact): Long =
        repository.addContact(contact)

    fun removeContactById(contactId: String) =
        repository.removeContactById(contactId)

    fun updateContact(contact: Contact) =
        repository.updateContact(contact)

    fun getAllMessagesById(contactId: String): List<ChatMessage> =
        repository.getAllMessagesById(contactId)

    fun addMessageById(message: ChatMessage, contactId: String) {
        repository.addMessageById(message, contactId)
        if (onNewMessageCreatedListener != null) {
            onNewMessageCreatedListener?.invoke(message)
        } else {
            Log.e(logTag, "listener not init")
        }
    }

    fun sendMessage(message: String, number: String) =
        SmsManager.getDefault().sendTextMessage(number, (null), message, (null), (null))

    fun setOnNewMessageCreatedListener(listener: (ChatMessage) -> Unit) {
        onNewMessageCreatedListener = listener
    }

    override fun getMessage(sms: Sms) {
        val id: String? =
            if (isNumberIndividual(sms.number)) {
                createNewContact(sms.number)
            } else {
                findContactIdByNumber(sms.number)
            }
        val message = mapper.mapSmsToMessage(sms)
        id?.let {
            addMessageById(message, id)
        } ?: Log.e(logTag, "contactId not find")
    }

    private fun createNewContact(number: String): String {
        val newId: String = getNewContactIndex()
        val contact = Contact(id = newId, name = number, number = number)
        return addContact(contact).toString()
    }
}