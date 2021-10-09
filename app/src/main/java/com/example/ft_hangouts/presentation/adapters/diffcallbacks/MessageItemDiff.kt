package com.example.ft_hangouts.presentation.adapters.diffcallbacks

import androidx.recyclerview.widget.DiffUtil
import com.example.ft_hangouts.domain.models.ChatMessage

class MessageItemDiff : DiffUtil.ItemCallback<ChatMessage>() {
    override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean =
        oldItem.userType == newItem.userType && oldItem.messageTime == newItem.messageTime
                && oldItem.messageText == newItem.messageText

    override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean =
        oldItem.userType == newItem.userType && oldItem.messageTime == newItem.messageTime &&
                oldItem.messageText == newItem.messageText
}