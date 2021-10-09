package com.example.ft_hangouts.presentation.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.ft_hangouts.databinding.ItemContactBinding
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.updateColor
import com.example.ft_hangouts.updateMultiplyColor

class ContactViewHolder(private val binding: ItemContactBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        contact: Contact,
        onChatIconClick: (Contact) -> Unit,
        onImgPersonClick: (Contact) -> Unit,
        onCallIconClick: (Contact) -> Unit,
        color: Int
    ) {
        setupViewText(contact)
        setupButtonColor(color)
        setupClickListeners(onChatIconClick, onImgPersonClick, onCallIconClick, contact)
    }

    private fun setupViewText(contact: Contact) =
        binding.apply {
            tvPersonFullName.text = contact.fullName
            tvPersonNumber.text = contact.number
        }

    private fun setupClickListeners(
        onChatIconClick: (Contact) -> Unit,
        onImgPersonClick: (Contact) -> Unit,
        onCallIconClick: (Contact) -> Unit,
        contact: Contact
    ) =
        binding.apply {
            buttonGoToChat.setOnClickListener {
                onChatIconClick(contact)
            }
            imgPerson.setOnClickListener {
                onImgPersonClick(contact)
            }
            buttonCallToContact.setOnClickListener {
                onCallIconClick(contact)
            }
        }

    private fun setupButtonColor(colorRes: Int) =
        binding.apply {
            buttonCallToContact.updateColor(colorRes)
            buttonGoToChat.updateColor(colorRes)
            imgPerson.updateMultiplyColor(colorRes)
        }
}