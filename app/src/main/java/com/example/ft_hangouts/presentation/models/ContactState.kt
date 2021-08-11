package com.example.ft_hangouts.presentation.models

import androidx.annotation.StringRes
import com.example.ft_hangouts.R

class ContactState(
    val contact: Contact? = null,
    val isNumberIndividual: Boolean = true
) {
    private val isNameNotEmpty: Boolean = contact?.name?.isNotBlank() == true

    val isContactCorrect: Boolean = contact != null && isNameNotEmpty && isNumberIndividual
//    val isErrorMassageVisible: Boolean = contact != null && !isCreateSuccess

    @StringRes
    val errorMessageResId: Int = when {
        !isNameNotEmpty -> R.string.empty_name_error_message
        !isNumberIndividual -> R.string.invalid_number_error_message
        contact != null && contact.number == null -> R.string.empty_number_error_message
        else -> R.string.empty_string
    }
}