package com.example.ft_hangouts.presentation.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ft_hangouts.domain.interactors.ColorInteractor
import com.example.ft_hangouts.domain.interactors.ContactInteractor
import com.example.ft_hangouts.domain.interactors.MessageInteractor
import com.example.ft_hangouts.presentation.mappers.ContactMapper
import com.example.ft_hangouts.presentation.viewmodels.*
import java.lang.IllegalArgumentException

class ContactViewModelFactory(
    private val colorInteractor: ColorInteractor,
    private val contactInteractor: ContactInteractor,
    private val messageInteractor: MessageInteractor,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            ContactsBookViewModel::class.java -> ContactsBookViewModel(colorInteractor, contactInteractor, ContactMapper()) as T
            ContactCreatorViewModel::class.java -> ContactCreatorViewModel(colorInteractor, contactInteractor, ContactMapper()) as T
            ContactDetailsViewModel::class.java -> ContactDetailsViewModel(colorInteractor, contactInteractor) as T
            ContactEditorViewModel::class.java -> ContactEditorViewModel(colorInteractor, contactInteractor, ContactMapper()) as T
            ContactChatViewModel::class.java -> ContactChatViewModel(colorInteractor, messageInteractor) as T
            RootViewModel::class.java -> RootViewModel(colorInteractor) as T
            else -> throw IllegalArgumentException("Factory cannot make ViewModel of type ${modelClass.simpleName}")
        }
}