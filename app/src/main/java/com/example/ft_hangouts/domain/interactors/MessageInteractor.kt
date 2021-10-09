package com.example.ft_hangouts.domain.interactors

import android.telephony.SmsManager
import android.util.Log
import com.example.ft_hangouts.data.repository.MessageRepository
import com.example.ft_hangouts.domain.models.ChatMessage

class MessageInteractor(private val repository: MessageRepository) {

    private val logTag: String = this::class.java.simpleName
    private var onNewMessageCreatedListener: ((ChatMessage) -> Unit)? = null

    fun getAllMessagesById(contactId: String): List<ChatMessage> =
        repository.getAllMessagesById(contactId)

    fun setOnNewMessageCreatedListener(listener: (ChatMessage) -> Unit) {
        onNewMessageCreatedListener = listener
    }

    fun addMessageById(message: ChatMessage, contactId: String) {
        repository.addMessageById(message, contactId)
        onNewMessageCreated(message)
    }

    private fun onNewMessageCreated(message: ChatMessage) =
        onNewMessageCreatedListener?.invoke(message) ?: Log.e(logTag, "listener not init")

    fun sendMessage(message: String, number: String) =
        SmsManager.getDefault().sendTextMessage(number, (null), message, (null), (null))
}