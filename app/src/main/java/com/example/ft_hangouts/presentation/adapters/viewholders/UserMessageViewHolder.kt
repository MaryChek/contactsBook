package com.example.ft_hangouts.presentation.adapters.viewholders

import com.example.ft_hangouts.databinding.ItemMyMessageBinding
import com.example.ft_hangouts.domain.models.ChatMessage

class UserMessageViewHolder(binding: ItemMyMessageBinding) :
    BaseMessageViewHolder<ItemMyMessageBinding>(binding) {

    override fun bind(chatMessage: ChatMessage) {
        binding.tvMessage.text = chatMessage.messageText
        binding.tvTime.text = chatMessage.messageTime
    }
}