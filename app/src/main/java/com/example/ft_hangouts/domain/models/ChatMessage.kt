package com.example.ft_hangouts.domain.models

import com.example.ft_hangouts.toSimpleDate
import java.util.*

class ChatMessage(
    val messageText: String,
    val userType: UserType = UserType.User,
    val messageTime: String = Date().toSimpleDate()
)
