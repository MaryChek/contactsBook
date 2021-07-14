package com.example.ft_hangouts.presentation.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.viewmodels.ContactCreatorViewModel
import com.example.ft_hangouts.presentation.viewmodels.ContactDetailsViewModel
import com.example.ft_hangouts.presentation.viewmodels.ContactEditorViewModel
import com.example.ft_hangouts.presentation.viewmodels.ContactsBookViewModel
import java.lang.IllegalArgumentException

class ContactViewModelFactory(
    private val interactor: ContactsInteractor
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            ContactsBookViewModel::class.java -> ContactsBookViewModel(interactor) as T
            ContactCreatorViewModel::class.java -> ContactCreatorViewModel(interactor) as T
            ContactDetailsViewModel::class.java -> ContactDetailsViewModel() as T
            ContactEditorViewModel::class.java -> ContactEditorViewModel(interactor) as T
            else -> throw IllegalArgumentException("Factory cannot make ViewModel of type ${modelClass.simpleName}")
        }
}