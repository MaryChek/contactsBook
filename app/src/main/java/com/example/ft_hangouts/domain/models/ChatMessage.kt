package com.example.ft_hangouts.domain.models

import java.util.*

class ChatMessage(
    val messageText: String,
    val userType: UserType = UserType.User,
    val messageTime: String = Date().toString()
)
