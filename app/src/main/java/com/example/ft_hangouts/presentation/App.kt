package com.example.ft_hangouts.presentation

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.data.room.database.DBContacts
import com.example.ft_hangouts.data.room.database.DBMessages
import com.example.ft_hangouts.data.room.helper.ContactDBHelper
import com.example.ft_hangouts.data.storage.ColorStorage
import com.example.ft_hangouts.data.storage.Prefs
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.domain.mappers.MessageMapper
import com.example.ft_hangouts.presentation.viewmodels.factory.ContactViewModelFactory

class App: Application() {

    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var interactor: ContactsInteractor

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        val dbHelper = ContactDBHelper(applicationContext, DATABASE_NAME, DATABASE_VERSION)
        val dbContacts = DBContacts(dbHelper)
        val dbMessages = DBMessages(dbHelper)
        val repository = ContactRepository(dbContacts, dbMessages, MessageMapper())
        val prefs = Prefs(applicationContext)
        val colorStorage = ColorStorage(prefs)
        interactor = ContactsInteractor(repository, MessageMapper(), colorStorage)
        viewModelFactory = ContactViewModelFactory(interactor)
    }

    companion object {
        private const val DATABASE_NAME = "Contacts.db"
        private const val DATABASE_VERSION = 1
    }
}