package com.example.ft_hangouts.presentation.viewmodels

import android.util.Log
import com.example.ft_hangouts.R
import com.example.ft_hangouts.domain.interactors.ColorInteractor
import com.example.ft_hangouts.domain.interactors.MessageInteractor
import com.example.ft_hangouts.domain.models.ChatMessage
import com.example.ft_hangouts.presentation.models.ChatState
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.navigation.FromContactChat
import com.example.ft_hangouts.presentation.viewmodels.base.BaseViewModel

class ContactChatViewModel(
    colorInteractor: ColorInteractor,
    private val interactor: MessageInteractor
) : BaseViewModel<ChatState, FromContactChat>(colorInteractor, ChatState()) {

    private val logTag: String = this::class.java.simpleName

    override fun onViewCreated() {
        super.onViewCreated()
        setOnNewMessageCreatedListener()
        updateAction(FromContactChat.Command.AccessSendSmsPermissions)
    }

    private fun setOnNewMessageCreatedListener() =
        interactor.setOnNewMessageCreatedListener { message ->
            onNewMessageCreated(message)
        }

    private fun onNewMessageCreated(message: ChatMessage) {
        val messages: MutableList<ChatMessage> = model.listMessage.toMutableList()
        messages.add(message)
        updateModel(messages = messages)
    }

    private fun updateModel(
        currentMessage: String = model.currentMessage,
        contact: Contact? = model.contact,
        messages: List<ChatMessage> = model.listMessage,
        hasPermissionToSend: Boolean = model.hasPermissionToSend
    ) {
        val chatState = ChatState(hasPermissionToSend, currentMessage, contact, messages)
        model = chatState
        updateScreen()
    }

    fun fetchAllMessages(contact: Contact) {
        val messages: List<ChatMessage> = getMessages(contact.id)
        updateModel(contact = contact, messages = messages)
    }
    
    private fun getMessages(contactId: String?): List<ChatMessage> =
        if (contactId != null) {
            interactor.getAllMessagesById(contactId)
        } else {
            errorLogMissingId()
            listOf()
        }

    fun onSendSmsPermissionResponse(isGranted: Boolean) {
        if (isGranted) {
            updateModel(hasPermissionToSend = true)
        } else {
            updateModel(hasPermissionToSend = false)
        }
    }

    fun onMessageSendClick() {
        clearMessageLine()
        if (model.currentMessage.isNotBlank()) {
            sendMessageIfPossible(model.currentMessage)
        }
    }

    private fun clearMessageLine() =
        updateAction(FromContactChat.Command.ClearEditTextAndSetEditorAction)

    private fun sendMessageIfPossible(currentMessage: String): Boolean {
        val chatMessage = ChatMessage(currentMessage)
        val interlocutorId = model.interlocutorId
        val number: String? = model.contact?.number

        return if (interlocutorId != null && number != null) {
            if (model.hasPermissionToSend) {
                sendMessage(chatMessage, interlocutorId, number)
            } else {
                showPermissionError()
            }
            true
        } else {
            errorLogMissingId()
            false
        }
    }

    private fun sendMessage(chatMessage: ChatMessage, interlocutorId: String, number: String) {
        interactor.addMessageById(chatMessage, interlocutorId)
        interactor.sendMessage(chatMessage.messageText, number)
    }

    private fun showPermissionError() {
        Log.e(logTag, "not permission")
        updateAction(FromContactChat.Command.ShowErrorMessage(R.string.error_send_message))
    }

    fun onMessageSubmit(newMessage: String) =
        updateModel(currentMessage = newMessage)

    override fun goToPrevious() =
        updateAction(FromContactChat.Navigate.PreviousScreen)

    private fun errorLogMissingId() =
        Log.e(logTag, "missing id")
}