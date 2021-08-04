package com.example.ft_hangouts.presentation.models

class ContactEditorState(
    updatedContact: Contact? = null,
    isNumberIndividual: Boolean? = null
): ContactState(updatedContact, isNumberIndividual)