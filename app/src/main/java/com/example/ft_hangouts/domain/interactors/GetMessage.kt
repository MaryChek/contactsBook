package com.example.ft_hangouts.domain.interactors

import com.example.ft_hangouts.presentation.receiver.model.Sms

interface GetMessage {
    fun getMessage(sms: Sms)
}