package com.example.ft_hangouts.presentation

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.viewmodels.factory.ContactViewModelFactory

class App: Application() {

    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        val repository = ContactRepository()
        val interactor = ContactsInteractor(repository)
        viewModelFactory = ContactViewModelFactory(interactor)
    }
}