package com.example.ft_hangouts.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.ft_hangouts.databinding.ItemContactBinding
import com.example.ft_hangouts.presentation.adapters.diffcallbacks.ContactItemDiff
import com.example.ft_hangouts.presentation.adapters.viewholders.ContactViewHolder
import com.example.ft_hangouts.presentation.models.Contact

class ContactsListAdapter(
    private val onChatIconClick: (Contact) -> Unit,
    private val onImgPersonClick: (Contact) -> Unit,
    private val onCallIconClick: (Contact) -> Unit,
    private var colorItems: Int
) : ListAdapter<Contact, ContactViewHolder>(ContactItemDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
        ContactViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) =
        holder.bind(
            getItem(position), onChatIconClick, onImgPersonClick, onCallIconClick, colorItems
        )

    fun updateColor(color: Int) {
        colorItems = color
        notifyDataSetChanged()
    }
}