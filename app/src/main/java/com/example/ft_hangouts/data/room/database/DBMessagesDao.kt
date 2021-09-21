package com.example.ft_hangouts.data.room.database

import com.example.ft_hangouts.data.room.model.ChatMessage

interface DBMessagesDao {
    fun getAllMessages(contactId: String): List<ChatMessage>

    fun addMessageById(message: ChatMessage, contactId: String)

    fun removeChatById(contactId: String)
}