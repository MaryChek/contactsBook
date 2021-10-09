package com.example.ft_hangouts.domain.interactors

import android.telephony.SmsManager
import android.util.Log
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.data.storage.ColorStorage
import com.example.ft_hangouts.domain.mappers.MessageMapper
import com.example.ft_hangouts.domain.models.ChatMessage
import com.example.ft_hangouts.domain.models.Contact
import com.example.ft_hangouts.presentation.models.ColorState
import com.example.ft_hangouts.presentation.receiver.model.Sms

class ContactsInteractor(
    private val repository: ContactRepository,
    private val mapper: MessageMapper,
    private val colorStorage: ColorStorage
) : GetMessage, ColorInteractor {

    private val logTag: String = this::class.java.simpleName
    private var onNewMessageCreatedListener: ((ChatMessage) -> Unit)? = null

    fun getAllContacts(): List<Contact> =
        repository.getAllContacts()

    fun getNewContactIndex(): String =
        getAllContacts().size.toString()

    fun isNumberIndividual(number: String, unlessId: String? = null): Boolean =
        getAllContacts().find { contact ->
            contact.numberEquals(number) && contact.id != unlessId
        } == null

    fun isNumberValid(number: String): Boolean =
        Contact(number = number).isValid()

    private fun findContactIdByNumber(number: String): String? =
        getAllContacts().find { contact ->
            contact.numberEquals(number)
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
        val id: String =
            findContactIdByNumber(sms.number) ?: createNewContact(sms.number)
        val message = mapper.mapSmsToMessage(sms)
        if (id == ERROR_ID) {
            Log.e(logTag, "contact create fail")
        }
        addMessageById(message, id)
    }

    private fun createNewContact(number: String): String {
        val newId: String = getNewContactIndex()
        val contact = Contact(id = newId, name = number, number = number)
        return addContact(contact).toString()
    }

    override fun getColor(): ColorState.Color {
        val color: Int = colorStorage.getColor(DEFAULT_COLOR_VALUE.ordinal)
        return mapper.mapColor(color)
    }

    override fun setColor(color: ColorState.Color) =
        colorStorage.setColor(color.ordinal)

    companion object {
        private const val ERROR_ID = "-1"
        private val DEFAULT_COLOR_VALUE = ColorState.Color.Purple
    }
}