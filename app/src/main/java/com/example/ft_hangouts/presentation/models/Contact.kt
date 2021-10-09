package com.example.ft_hangouts.presentation.models

import androidx.annotation.ColorRes
import java.io.Serializable

open class Contact(
    val id: String? = null,
    val name: String? = null,
    val lastName: String? = null,
    val number: String? = null,
    val email: String? = null,
    val imagePath: String? = null,
) : Serializable {

    val fullName: String? =
        if (lastName != null) {
            "$name $lastName"
        } else {
            name
        }
}