package com.example.ft_hangouts.presentation.models

import androidx.annotation.StringRes
import com.example.ft_hangouts.R
import com.example.ft_hangouts.domain.models.Contact

class ContactState(
    val contact: Contact? = null,
    val isNumberIndividual: Boolean? = null,
) {
    private val isNameNotEmpty: Boolean = contact?.name?.isNotBlank() == true

    val isCreateSuccess: Boolean = contact != null && isNameNotEmpty && isNumberIndividual == true
    val isErrorMassageVisible: Boolean = contact != null && !isCreateSuccess

    @StringRes
    val errorMessageResId: Int = when {
        !isNameNotEmpty -> R.string.empty_name_error_message
        isNumberIndividual == false -> R.string.invalid_number_error_message
        else -> R.string.empty_string
    }
}