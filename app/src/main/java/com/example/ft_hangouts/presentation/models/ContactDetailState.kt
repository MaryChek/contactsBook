package com.example.ft_hangouts.presentation.models

class ContactDetailState(
    contact: Contact,
) : Contact(
    contact.id,
    contact.name,
    contact.lastName,
    contact.number,
    contact.email,
    contact.imagePath
) {
    val isEmailVisible: Boolean = !email.isNullOrBlank()
}