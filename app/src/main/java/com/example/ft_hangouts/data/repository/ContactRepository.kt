package com.example.ft_hangouts.data.repository

import com.example.ft_hangouts.data.room.database.DBContacts
import com.example.ft_hangouts.data.room.database.DBMessages
import com.example.ft_hangouts.domain.mappers.MessageMapper
import com.example.ft_hangouts.domain.models.ChatMessage
import com.example.ft_hangouts.domain.models.Contact

class ContactRepository(
    private val dbContacts: DBContacts,
    private val dbMessages: DBMessages,
    private val mapper: MessageMapper
) {
    fun getAllContacts(): List<Contact> =
        dbContacts.getAllContacts()

    fun addContact(contact: Contact) =
        dbContacts.addContact(contact)

    fun removeContactById(contactId: String) {
        dbContacts.removeContactById(contactId)
        dbMessages.removeChatById(contactId)
    }

    fun updateContact(contact: Contact) =
        dbContacts.updateContact(contact)

    fun getAllMessagesById(contactId: String): List<ChatMessage> =
        mapper.mapMessages(
            dbMessages.getAllMessages(contactId)
        )

    fun addMessageById(message: ChatMessage, contactId: String) {
        val currentMessage = mapper.map(message)
        dbMessages.addMessageById(currentMessage, contactId)
    }
}