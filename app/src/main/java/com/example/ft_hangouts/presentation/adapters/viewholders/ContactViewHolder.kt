package com.example.ft_hangouts.presentation.adapters.viewholders

import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import com.example.ft_hangouts.databinding.ItemContactBinding
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.updateColor

class ContactViewHolder(private val binding: ItemContactBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        contact: Contact,
        chatIconClickListener: (Contact) -> Unit,
        imgPersonClickListener: (Contact) -> Unit,
        color: Int
    ) {
        setupViewText(contact)
        setupButtonColor(color)
        setupClickListeners(chatIconClickListener, imgPersonClickListener, contact)
    }

    private fun setupViewText(contact: Contact) =
        binding.apply {
            tvPersonFullName.text = contact.fullName
            tvPersonNumber.text = contact.number
        }

    private fun setupClickListeners(
        chatIconClickListener: (Contact) -> Unit,
        imgPersonClickListener: (Contact) -> Unit,
        contact: Contact
    ) =
        binding.apply {
            buttonGoToChat.setOnClickListener {
                chatIconClickListener(contact)
            }
            imgPerson.setOnClickListener {
                imgPersonClickListener(contact)
            }
        }

    private fun setupButtonColor(@ColorRes colorRes: Int) =
        binding.apply {
            buttonCallToContact.updateColor(colorRes)
            buttonGoToChat.updateColor(colorRes)
        }
}