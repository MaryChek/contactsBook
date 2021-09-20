package com.example.ft_hangouts.presentation.adapters.viewholders

import com.example.ft_hangouts.databinding.ItemYourMessageBinding
import com.example.ft_hangouts.domain.models.ChatMessage

class OtherUserMessageViewHolder(binding: ItemYourMessageBinding) :
    BaseMessageViewHolder<ItemYourMessageBinding>(binding) {

    override fun bind(chatMessage: ChatMessage) {
        binding.tvMessage.text = chatMessage.messageText
        binding.tvTime.text = chatMessage.messageTime
    }
}