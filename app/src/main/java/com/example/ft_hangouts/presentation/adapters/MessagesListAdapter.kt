package com.example.ft_hangouts.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.ft_hangouts.domain.models.ChatMessage
import com.example.ft_hangouts.presentation.adapters.diffcallbacks.MessageItemDiff
import com.example.ft_hangouts.presentation.adapters.viewholders.BaseMessageViewHolder
import com.example.ft_hangouts.presentation.adapters.viewholders.MessageViewHolderFactory

class MessagesListAdapter: ListAdapter<ChatMessage, BaseMessageViewHolder<*>>(MessageItemDiff()) {

    override fun getItemViewType(position: Int): Int =
        getItem(position).userType.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMessageViewHolder<*> =
        MessageViewHolderFactory().createViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: BaseMessageViewHolder<*>, position: Int) {
        holder.bind(getItem(position))
    }
}