package com.example.ft_hangouts.presentation.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.ft_hangouts.databinding.ItemContactBinding
import com.example.ft_hangouts.presentation.models.Contact

class ContactViewHolder(private val binding: ItemContactBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        contact: Contact,
        chatIconClickListener: (Contact) -> Unit,
        imgPersonClickListener: (Contact) -> Unit
    ) {
        binding.apply {
            tvPersonName.text = contact.fullName // TODO rename to tvPersonFullName
            tvPersonNumber.text = contact.number
            buttonGoToChat.setOnClickListener {
                chatIconClickListener(contact)
            }
            imgPerson.setOnClickListener {
                imgPersonClickListener(contact)
            }
        }
    }
}