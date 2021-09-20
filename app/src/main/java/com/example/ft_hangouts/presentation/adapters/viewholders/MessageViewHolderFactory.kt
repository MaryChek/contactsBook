package com.example.ft_hangouts.presentation.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ft_hangouts.databinding.ItemMyMessageBinding
import com.example.ft_hangouts.databinding.ItemYourMessageBinding
import com.example.ft_hangouts.domain.models.UserType

class MessageViewHolderFactory {
    fun createViewHolder(parent: ViewGroup, viewType: Int): BaseMessageViewHolder<*> =
        when (viewType) {
            UserType.User.ordinal -> createUserViewHolder(parent)
            else -> createOtherUserViewHolder(parent)
        }

    private fun createUserViewHolder(parent: ViewGroup): UserMessageViewHolder =
        UserMessageViewHolder(
            ItemMyMessageBinding.inflate(LayoutInflater.from(parent.context), parent, (false))
        )

    private fun createOtherUserViewHolder(parent: ViewGroup): OtherUserMessageViewHolder =
        OtherUserMessageViewHolder(
            ItemYourMessageBinding.inflate(LayoutInflater.from(parent.context), parent, (false))
        )
}