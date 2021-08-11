package com.example.ft_hangouts.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.ft_hangouts.presentation.fragments.base.BaseContactEditorFragment
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.models.ContactState
import com.example.ft_hangouts.presentation.navigation.FromContactEditor
import com.example.ft_hangouts.presentation.navigation.router.ContactEditorRouter
import com.example.ft_hangouts.presentation.viewmodels.ContactEditorViewModel

class ContactEditorFragment : BaseContactEditorFragment<
        FromContactEditor, FromContactEditor.Navigate,
        ContactEditorRouter, ContactEditorViewModel>() {

    private val logTag: String = this::class.java.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val contact: Contact? =
            arguments?.getSerializable(Contact::class.java.simpleName) as Contact?

        contact?.let {
            viewModel.init(contact)
        } ?: Log.e(logTag, "Missing Contact")
    }

    override fun getViewModelClass(): Class<ContactEditorViewModel> =
        ContactEditorViewModel::class.java

    override fun getNavRouter(): ContactEditorRouter =
        ContactEditorRouter(navController)

    override fun navigateTo(destination: FromContactEditor) =
        when (destination) {
            is FromContactEditor.Command.ShowErrorMessage ->
                showErrorMessage(destination.errorMessageResId)
            is FromContactEditor.Navigate ->
                router.goToScreen(destination)
        }

    override fun updateScreen(model: ContactState) {
        binding?.edtName?.setText(model.contact?.name)
        binding?.edtNumber?.setText(model.contact?.number)
        binding?.edtLastName?.setText(model.contact?.lastName)
        binding?.edtEmail?.setText(model.contact?.email)
    }
}

