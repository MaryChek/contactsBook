package com.example.ft_hangouts.presentation

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.ViewModelProvider
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.data.room.database.DBContacts
import com.example.ft_hangouts.data.room.helper.ContactDBHelper
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.mappers.ContactMapper
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
        val repository = ContactRepository(dbContacts)
        val interactor = ContactsInteractor(repository)
        viewModelFactory = ContactViewModelFactory(interactor)
    }

    companion object {
        private const val DATABASE_NAME = "Contacts.db"
        private const val DATABASE_VERSION = 1
    }
}