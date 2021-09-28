package com.example.ft_hangouts.presentation.receiver.model

import java.util.*

class Sms(
    val number: String,
    val message: String,
    val timeSend: String = Date().toString(),

)