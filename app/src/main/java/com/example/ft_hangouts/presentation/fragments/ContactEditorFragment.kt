package com.example.ft_hangouts.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.ft_hangouts.presentation.fragments.base.BaseContactEditorFragment
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.models.ContactEditorState
import com.example.ft_hangouts.presentation.navigation.FromContactEditor
import com.example.ft_hangouts.presentation.navigation.router.ContactEditorRouter
import com.example.ft_hangouts.presentation.viewmodels.ContactEditorViewModel

class ContactEditorFragment : BaseContactEditorFragment<
            ContactEditorState, FromContactEditor, ContactEditorRouter, ContactEditorViewModel>() {

    private val logTag: String = this::class.java.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contact: Contact? =
            arguments?.getSerializable(Contact::class.java.simpleName) as Contact?
        if (contact != null) {
            init(contact)
        } else {
            Log.e(logTag, "Missing Contact")
        }
    }

    private fun init(contact: Contact) =
        viewModel.init(contact)

    override fun getViewModelClass(): Class<ContactEditorViewModel> =
        ContactEditorViewModel::class.java

    override fun getNavRouter(): ContactEditorRouter =
        ContactEditorRouter(navController)

    override fun goToScreen(destination: FromContactEditor) =
        when (destination) {
            is FromContactEditor.ShowErrorMessage -> showErrorMessage(destination.errorMessageResId)
            else -> router.goToScreen(destination)
        }

    override fun updateScreen(model: ContactEditorState) {
        binding?.edtName?.setText(model.contact?.name)
        binding?.edtNumber?.setText(model.contact?.number)
    }
}