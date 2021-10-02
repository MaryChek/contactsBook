package com.example.ft_hangouts.presentation.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.domain.mappers.MessageMapper
import com.example.ft_hangouts.presentation.mappers.ContactMapper
import com.example.ft_hangouts.presentation.viewmodels.*
import java.lang.IllegalArgumentException

class ContactViewModelFactory(
    private val interactor: ContactsInteractor,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            ContactsBookViewModel::class.java -> ContactsBookViewModel(interactor, ContactMapper()) as T
            ContactCreatorViewModel::class.java -> ContactCreatorViewModel(interactor, ContactMapper()) as T
            ContactDetailsViewModel::class.java -> ContactDetailsViewModel(interactor) as T
            ContactEditorViewModel::class.java -> ContactEditorViewModel(interactor, ContactMapper()) as T
            ContactChatViewModel::class.java -> ContactChatViewModel(interactor, MessageMapper()) as T
            RootViewModel::class.java -> RootViewModel(interactor) as T
            else -> throw IllegalArgumentException("Factory cannot make ViewModel of type ${modelClass.simpleName}")
        }
}