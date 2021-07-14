package com.example.ft_hangouts.presentation.models

class ContactEditorState(
    val currentContact: Contact? = null,
    updatedContact: Contact? = currentContact,
    isNumberIndividual: Boolean? = null
): ContactState(updatedContact, isNumberIndividual)