package com.example.ft_hangouts.presentation.receiver

import com.example.ft_hangouts.presentation.receiver.model.Sms

interface SmsReceiveListener {
    fun onSmsReceive(sms: Sms)
}