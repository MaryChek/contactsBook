package com.example.ft_hangouts.presentation.models

import com.example.ft_hangouts.domain.models.ChatMessage

class ChatState(
    val currentMessage: String = "",
    val contact: Contact? = null,
    val listMessage: List<ChatMessage> = listOf()
) {
    val interlocutorId: String? = contact?.id
    val interlocutorName: String? = contact?.name ?: contact?.number
    val rvScrollPosition: Int = listMessage.size
}
