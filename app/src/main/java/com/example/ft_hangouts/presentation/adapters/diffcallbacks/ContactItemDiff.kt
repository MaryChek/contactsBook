package com.example.ft_hangouts.presentation.adapters.diffcallbacks

import androidx.recyclerview.widget.DiffUtil
import com.example.ft_hangouts.domain.models.Contact

class ContactItemDiff : DiffUtil.ItemCallback<Contact>() {
    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
        oldItem.name == newItem.name && oldItem.number == newItem.number

    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
        oldItem.name == newItem.name && oldItem.number == newItem.number
}