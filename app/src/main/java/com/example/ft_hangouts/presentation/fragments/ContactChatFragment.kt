package com.example.ft_hangouts.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.ft_hangouts.databinding.FragmentContactChatBinding
import com.example.ft_hangouts.presentation.adapters.MessagesListAdapter
import com.example.ft_hangouts.presentation.fragments.base.BaseContactWithEditTextFragment
import com.example.ft_hangouts.presentation.models.ChatState
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.navigation.FromContactChat
import com.example.ft_hangouts.presentation.navigation.router.ContactChatRouter
import com.example.ft_hangouts.presentation.viewmodels.ContactChatViewModel

class ContactChatFragment : BaseContactWithEditTextFragment<
        ChatState, FromContactChat, FromContactChat.Navigate,
        ContactChatRouter, ContactChatViewModel>() {

    private val logTag: String = this::class.java.simpleName
    private var binding: FragmentContactChatBinding? = null
    private var adapter: MessagesListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactChatBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        initButtonClickListener()
        initEditTextSubmitListeners()
        initMessageList()
        init()
    }

    private fun initMessageList() {
        adapter = MessagesListAdapter()
//        registerAdapterDataObserver()
        binding?.rvMessages?.adapter = adapter
//        binding?.rvMessages?.smoothScrollToPosition(adapter?.itemCount ?: 0)
    }

    private fun registerAdapterDataObserver() {
        adapter?.registerAdapterDataObserver(
            (object : AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    binding?.rvMessages?.smoothScrollToPosition(adapter?.itemCount ?: 0)
                }
            })
        )
    }

    private fun init() {
        val contact: Contact? =
            arguments?.getSerializable(Contact::class.java.simpleName) as Contact?
        if (contact != null) {
            viewModel.fetchAllMessages(contact)
        } else {
            Log.e(logTag, "Missing Contact")
        }
    }

    private fun initButtonClickListener() {
        binding?.buttonSend?.setOnClickListener {
            viewModel.onMessageSendClick()
        }
    }

    private fun initEditTextSubmitListeners() {
        binding?.input?.setOnTextSubmitListener(
            viewModel::onMessageSubmit,
            shouldDoBasicActionByActionId = { actionId -> actionId == EditorInfo.IME_ACTION_DONE }
        )
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun updateScreen(model: ChatState) {
        adapter?.submitList(model.listMessage)
        binding?.rvMessages?.smoothScrollToPosition(model.rvScrollPosition)
    }

    override fun getNavRouter(): ContactChatRouter =
        ContactChatRouter(findNavController())

    override fun getViewModelClass(): Class<ContactChatViewModel> =
        ContactChatViewModel::class.java

    override fun navigateTo(destination: FromContactChat) =
        when (destination) {
            is FromContactChat.Navigate ->
                router.goToScreen(destination)
            is FromContactChat.Command.ClearEditTextAndSetEditorAction ->
                clearEditTextAndSetEditorAction()
        }

    private fun clearEditTextAndSetEditorAction() {
        binding?.input?.onEditorAction(EditorInfo.IME_ACTION_SEND)
        binding?.input?.text?.clear()
    }
}