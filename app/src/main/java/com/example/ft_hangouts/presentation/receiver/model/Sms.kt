package com.example.ft_hangouts.presentation.receiver.model

import com.example.ft_hangouts.toSimpleDate
import java.util.*

class Sms(
    val number: String,
    val message: String,
    val timeSend: String = Date().toSimpleDate(),
)