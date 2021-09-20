package com.example.ft_hangouts.presentation.viewmodels

import android.util.Log
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.domain.mappers.MessageMapper
import com.example.ft_hangouts.domain.models.ChatMessage
import com.example.ft_hangouts.presentation.models.ChatState
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.navigation.FromContactChat
import com.example.ft_hangouts.presentation.viewmodels.base.BaseViewModel

class ContactChatViewModel(
    private val interactor: ContactsInteractor,
    private val mapper: MessageMapper,
) :
    BaseViewModel<ChatState, FromContactChat>(ChatState()) {

    private val logTag: String = this::class.java.simpleName

    fun init() {
        interactor.setOnNewMessageCreatedListener(this::onNewMessageCreated)
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
    ) {
        val chatState = ChatState(currentMessage, contact, messages)
        model = chatState
        updateScreen()
    }

    fun fetchAllMessages(contact: Contact) {
        val messages: List<ChatMessage> =
            if (contact.id != null) {
                interactor.getAllMessagesById(contact.id)
            } else {
                errorLogMissingId()
                listOf()
            }

        updateModel( contact = contact, messages = messages)
    }

    fun onMessageSendClick() {
        clearMessageLine()
        if (model.currentMessage.isNotBlank()) {
            sendMessage(model.currentMessage)
        }
    }

    private fun clearMessageLine() =
        goToScreen(FromContactChat.Command.ClearEditTextAndSetEditorAction)

    private fun sendMessage(currentMessage: String): Boolean {
        val chatMessage = ChatMessage(currentMessage)
        val interlocutorId = model.interlocutorId

        return if (interlocutorId != null) {
            interactor.addMessageById(chatMessage, interlocutorId)
            true
        } else {
            errorLogMissingId()
            false
        }
    }

    fun onMessageSubmit(newMessage: String) =
        updateModel(currentMessage = newMessage)

    override fun goToPrevious() =
        goToScreen(FromContactChat.Navigate.PreviousScreen)

    private fun errorLogMissingId() =
        Log.e(logTag, "missing id")
}