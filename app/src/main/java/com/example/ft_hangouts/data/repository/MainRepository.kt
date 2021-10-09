package com.example.ft_hangouts.data.repository

import com.example.ft_hangouts.data.room.database.DBContacts
import com.example.ft_hangouts.data.room.database.DBMessages
import com.example.ft_hangouts.domain.mappers.MessageMapper
import com.example.ft_hangouts.domain.models.ChatMessage
import com.example.ft_hangouts.domain.models.Contact

class MainRepository(
    private val dbContacts: DBContacts,
    private val dbMessages: DBMessages,
    private val mapper: MessageMapper
): ContactRepository, MessageRepository {
    override fun getAllContacts(): List<Contact> =
        dbContacts.getAllContacts()

    override fun addContact(contact: Contact): Long =
        dbContacts.addContact(contact)

    override fun removeContactById(contactId: String) {
        dbContacts.removeContactById(contactId)
        dbMessages.removeChatById(contactId)
    }

    override fun updateContact(contact: Contact) =
        dbContacts.updateContact(contact)

    override fun getAllMessagesById(contactId: String): List<ChatMessage> =
        mapper.mapMessages(
            dbMessages.getAllMessages(contactId)
        )

    override fun addMessageById(message: ChatMessage, contactId: String) {
        val currentMessage = mapper.map(message)
        dbMessages.addMessageById(currentMessage, contactId)
    }
}