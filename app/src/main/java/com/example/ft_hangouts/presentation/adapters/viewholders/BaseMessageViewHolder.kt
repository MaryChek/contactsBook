package com.example.ft_hangouts.presentation.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.ft_hangouts.domain.models.ChatMessage

abstract class BaseMessageViewHolder<T : ViewBinding>(protected val binding: T) :
    RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(chatMessage: ChatMessage)
}