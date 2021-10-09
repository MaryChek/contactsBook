package com.example.ft_hangouts.domain.interactors

import com.example.ft_hangouts.domain.mappers.MessageMapper
import com.example.ft_hangouts.presentation.receiver.model.Sms

class MainInteractor(
    private val contactInteractor: ContactInteractor,
    private val messageInteractor: MessageInteractor,
    private val mapper: MessageMapper
) {

    fun getMessage(sms: Sms) {
        val id: String = contactInteractor.getContactIdByNumberOrCreateNew(sms.number)
        val message = mapper.mapSmsToMessage(sms)
        messageInteractor.addMessageById(message, id)
    }
}