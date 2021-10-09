package com.example.ft_hangouts.presentation.viewmodels

import android.content.Intent
import android.util.Log
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.domain.mappers.MessageMapper
import com.example.ft_hangouts.domain.models.ChatMessage
import com.example.ft_hangouts.domain.models.Contact
import com.example.ft_hangouts.domain.models.UserType
import com.example.ft_hangouts.getSeconds
import com.example.ft_hangouts.presentation.navigation.FromMainActivity
import com.example.ft_hangouts.presentation.viewmodels.base.BaseViewModel
import java.util.*

class RootViewModel(
    override val interactor: ContactsInteractor,
) : BaseViewModel<Any, FromMainActivity>(interactor, Any()) {

    private val logTag: String = this::class.java.simpleName
    private var backgroundTime: Int? = null

//    fun onSmsReceive(intent: Intent) {
//        val number: String? = intent.extras?.getString("number")
//        val message: String? = intent.extras?.getString("message")
//        val time: String? = intent.extras?.getString("time")
//        if (number == null) {
//            Log.e(logTag, "number is missing")
//            return
//        } else {
//            if (message != null && time != null) {
//                val sms = ChatMessage(message, UserType.ThirdPartyUser, time)
//                sendMessage(sms, number)
//            }
//        }
//    }
//
//    private fun sendMessage(sms: ChatMessage, number: String) {
//        val id: String? =
//            if (interactor.isNumberIndividual(number)) {
//                createNewContact(number)
//            } else {
//                interactor.findContactIdByNumber(number)
//            }
//
//        id?.let {
//            interactor.addMessageById(sms, id)
//        } ?: Log.e(logTag, "contactId not find")
//    }

//    private fun createNewContact(number: String): String {
//        val newId: String = interactor.getNewContactIndex()
//        val contact = Contact(id = newId, name = number, number = number)
//        interactor.addContact(contact)
//        return newId
//    }

    fun onActivityResume() {
        backgroundTime?.let { time ->
            val howTime: Int = getSeconds() - time
            goToScreen(FromMainActivity.Navigate.ShowToast(howTime))
        }
    }

    fun onActivityPause() {
        backgroundTime = getSeconds()
    }

    override fun goToPrevious() {

    }
}
