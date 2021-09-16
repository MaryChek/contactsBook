package com.example.ft_hangouts.domain.mappers

import com.example.ft_hangouts.data.room.model.ChatMessage
import com.example.ft_hangouts.domain.models.UserType
import com.example.ft_hangouts.domain.models.ChatMessage as DomainChatMessage

class MessageMapper {
    fun mapMessages(messages: List<ChatMessage>): List<DomainChatMessage> =
        messages.map { message ->
            DomainChatMessage(
                message.messageText,
                mapUserType(message.userType),
                message.data,
            )
        }

    private fun mapUserType(type: Int): UserType =
        if (type == UserType.User.ordinal) {
            UserType.User
        } else {
            UserType.ThirdPartyUser
        }

    fun map(message: DomainChatMessage): ChatMessage =
        ChatMessage(
            message.messageText,
            message.userType.ordinal,
            message.messageTime,
        )
}