package com.example.ft_hangouts.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ft_hangouts.databinding.FragmentContactDetailBinding
import com.example.ft_hangouts.presentation.fragments.base.BaseViewModelFragment
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.navigation.FromContactDetails
import com.example.ft_hangouts.presentation.viewmodels.ContactDetailsViewModel

class ContactDetailsFragment
    : BaseViewModelFragment<Contact, FromContactDetails, ContactDetailsViewModel>() {
    private var binding: FragmentContactDetailBinding? = null

    private val logTag: String = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

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

    private fun init(contact: Contact) {
        viewModel.init(contact)
        initButtonEditContactClickListener()
    }

    private fun initButtonEditContactClickListener() {
        binding?.buttonEditContact?.setOnClickListener { viewModel.onEditContactClick() }
    }

    override fun getViewModelClass(): Class<ContactDetailsViewModel> =
        ContactDetailsViewModel::class.java

    override fun goToScreen(destination: FromContactDetails): Any =
        when (destination) {
            is FromContactDetails.ContactEditor ->
                navigate(destination.navigateToId, destination.contact)
            is FromContactDetails.PreviousScreen -> navigateToPrevious()
        }

    override fun updateScreen(model: Contact) {
        binding?.tvPersonName?.text = model.name
        binding?.tvPersonNumber?.text = model.number
    }
}