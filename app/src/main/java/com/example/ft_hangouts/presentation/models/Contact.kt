package com.example.ft_hangouts.presentation.models

import java.io.Serializable

class Contact(
    val id: String? = null,
    val name: String? = null,
    val lastName: String? = null,
    val number: String? = null,
    val email: String? = null,
    val imagePath: String? = null,
) : Serializable