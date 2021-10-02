package com.example.ft_hangouts.presentation.mappers

import com.example.ft_hangouts.domain.models.ChatMessage
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.domain.models.Contact as DomainContact

class ContactMapper {
    fun mapContacts(contacts: List<DomainContact>): List<Contact> =
        contacts.map { contact ->
            Contact(
                contact.id,
                contact.name,
                contact.lastName,
                contact.number,
                contact.email,
                contact.imagePath,
            )
        }

    fun mapContact(contact: Contact): DomainContact =
        DomainContact(
            contact.id,
            contact.name,
            contact.lastName,
            contact.number,
            contact.email,
            contact.imagePath,
        )

    fun mapMessage(message: ChatMessage): ChatMessage =
        ChatMessage(
            message.messageText,
            message.userType,
            message.messageTime
        )
}