package com.example.ft_hangouts.data.repository

import com.example.ft_hangouts.domain.models.ChatMessage

interface MessageRepository {
    fun getAllMessagesById(contactId: String): List<ChatMessage>

    fun addMessageById(message: ChatMessage, contactId: String)
}