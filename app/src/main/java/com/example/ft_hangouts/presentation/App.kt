package com.example.ft_hangouts.presentation

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.data.room.database.DBContacts
import com.example.ft_hangouts.data.room.database.DBMessages
import com.example.ft_hangouts.data.room.helper.ContactDBHelper
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.domain.interactors.GetMessage
import com.example.ft_hangouts.domain.mappers.MessageMapper
import com.example.ft_hangouts.presentation.receiver.SmsBroadcastReceiver
import com.example.ft_hangouts.presentation.receiver.SmsReceiveListener
import com.example.ft_hangouts.presentation.receiver.model.Sms
import com.example.ft_hangouts.presentation.viewmodels.factory.ContactViewModelFactory

class App: Application() {

    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        val dbHelper = ContactDBHelper(applicationContext, DATABASE_NAME, DATABASE_VERSION)
        val dbContacts = DBContacts(dbHelper)
        val dbMessages = DBMessages(dbHelper)
        val repository = ContactRepository(dbContacts, dbMessages, MessageMapper())
        val interactor = ContactsInteractor(repository, MessageMapper())
        viewModelFactory = ContactViewModelFactory(interactor)
        initSmsListener(interactor)
    }


    private fun initSmsListener(interactor: GetMessage) {
        val smsReceive = SmsBroadcastReceiver()
        smsReceive.setOnSmsReceiveListener(
            object : SmsReceiveListener {
                override fun onSmsReceive(sms: Sms) {
                    interactor.getMessage(sms)
                }
            }
        )
    }

    companion object {
        private const val DATABASE_NAME = "Contacts.db"
        private const val DATABASE_VERSION = 1
    }
}