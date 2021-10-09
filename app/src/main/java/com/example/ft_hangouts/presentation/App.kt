package com.example.ft_hangouts.presentation

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.ft_hangouts.data.repository.MainRepository
import com.example.ft_hangouts.data.room.database.DBContacts
import com.example.ft_hangouts.data.room.database.DBMessages
import com.example.ft_hangouts.data.room.helper.ContactDBHelper
import com.example.ft_hangouts.data.storage.ColorStorage
import com.example.ft_hangouts.data.storage.Prefs
import com.example.ft_hangouts.domain.interactors.ColorInteractor
import com.example.ft_hangouts.domain.interactors.ContactInteractor
import com.example.ft_hangouts.domain.interactors.MainInteractor
import com.example.ft_hangouts.domain.interactors.MessageInteractor
import com.example.ft_hangouts.domain.mappers.MessageMapper
import com.example.ft_hangouts.presentation.viewmodels.factory.ContactViewModelFactory

class App: Application() {

    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var interactor: MainInteractor

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        val dbHelper = ContactDBHelper(applicationContext, DATABASE_NAME, DATABASE_VERSION)
        val dbContacts = DBContacts(dbHelper)
        val dbMessages = DBMessages(dbHelper)
        val repository = MainRepository(dbContacts, dbMessages, MessageMapper())
        val prefs = Prefs(applicationContext)
        val colorStorage = ColorStorage(prefs)
        val colorInteractor = ColorInteractor(colorStorage)
        val contactInteractor = ContactInteractor(repository)
        val messageInteractor = MessageInteractor(repository)
        interactor = MainInteractor(contactInteractor, messageInteractor, MessageMapper())
        viewModelFactory = ContactViewModelFactory(colorInteractor, contactInteractor, messageInteractor)
    }

    companion object {
        private const val DATABASE_NAME = "Contacts.db"
        private const val DATABASE_VERSION = 1
    }
}