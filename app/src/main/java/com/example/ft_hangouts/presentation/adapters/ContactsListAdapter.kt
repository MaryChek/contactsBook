package com.example.ft_hangouts.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.ft_hangouts.databinding.ItemContactBinding
import com.example.ft_hangouts.presentation.adapters.diffcallbacks.ContactItemDiff
import com.example.ft_hangouts.presentation.adapters.viewholders.ContactViewHolder
import com.example.ft_hangouts.presentation.models.Contact

class ContactsListAdapter(
    private val chatIconClickListener: (Contact) -> Unit,
    private val imgPersonClickListener: (Contact) -> Unit,
    private var colorItems: Int
) : ListAdapter<Contact, ContactViewHolder>(ContactItemDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
        ContactViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position), chatIconClickListener, imgPersonClickListener, colorItems)
    }

    fun updateColor(color: Int) {
        colorItems = color
        notifyDataSetChanged()
    }
}