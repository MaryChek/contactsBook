package com.example.ft_hangouts.domain.models

import androidx.core.text.isDigitsOnly

class Contact(
    val id: String? = null,
    val name: String? = null,
    val lastName: String? = null,
    val number: String? = null,
    val email: String? = null,
    val imagePath: String? = null,
) {
    fun isValid(): Boolean =
        number?.isConsistOnlyDigits() ?: false

    private val realNumber: String? = number?.toRealNumber()

    private fun String.toRealNumber(): String =
        if (isNotBlank() && first() == '+') {
            getWithoutFirst().incrementFirstDigit()
        } else {
            this
        }

    private fun String.isConsistOnlyDigits(): Boolean =
        if (first() == '+') {
            getWithoutFirst().isDigitsOnly()
        } else {
            isDigitsOnly()
        }

    private fun String.getWithoutFirst(): String =
        substring(1)

    private fun String.incrementFirstDigit(): String =
        first().digitToInt().plus(1).toString() + substring(1)

    fun numberEquals(otherNumber: String): Boolean =
        realNumber == otherNumber.toRealNumber()
}