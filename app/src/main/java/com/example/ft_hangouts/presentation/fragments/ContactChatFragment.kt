package com.example.ft_hangouts.presentation.fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.fragment.findNavController
import com.example.ft_hangouts.databinding.FragmentContactChatBinding
import com.example.ft_hangouts.domain.models.ColorState
import com.example.ft_hangouts.getColor
import com.example.ft_hangouts.presentation.activities.MainActivity
import com.example.ft_hangouts.presentation.adapters.MessagesListAdapter
import com.example.ft_hangouts.presentation.fragments.base.BaseContactWithEditTextFragment
import com.example.ft_hangouts.presentation.models.ChatState
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.navigation.FromContactChat
import com.example.ft_hangouts.presentation.navigation.router.ContactChatRouter
import com.example.ft_hangouts.presentation.viewmodels.ContactChatViewModel
import com.example.ft_hangouts.updateColor

class ContactChatFragment : BaseContactWithEditTextFragment<
        ChatState, FromContactChat, FromContactChat.Navigate,
        ContactChatRouter, ContactChatViewModel>(), RegistrationActivityResult {

    override val logTag: String = this::class.java.simpleName

    private var binding: FragmentContactChatBinding? = null
    private var adapter: MessagesListAdapter? = null
    private var requestPermissionForSendSmsLauncher: ActivityResultLauncher<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityResultLaunchers()
    }

    private fun initActivityResultLaunchers() {
        requestPermissionForSendSmsLauncher =
            registerForRequestPermissionResult(viewModel::onSendSmsPermissionResponse)
    }

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
        initButtonClickListener()
        initEditTextSubmitListeners()
        initMessageList()
        init()
    }

    private fun initMessageList() {
        adapter = MessagesListAdapter()
        binding?.rvMessages?.adapter = adapter
    }

    private fun init() {
        val contact: Contact? =
            arguments?.getSerializable(Contact::class.java.simpleName) as Contact?
        if (contact != null) {
            viewModel.fetchAllMessages(contact)
            setToolbarTitle(contact.name ?: contact.number ?: "")
        } else {
            Log.e(logTag, "Missing Contact")
        }
    }

    private fun setToolbarTitle(title: String) {
        (activity as MainActivity).supportActionBar?.title = title
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
            is FromContactChat.Command.AccessSendSmsPermissions ->
                accessSendSmsPermissions()
            is FromContactChat.Command.ShowErrorMessage ->
                showErrorMessage(destination.errorMessage)
        }

    private fun accessSendSmsPermissions() {
        requestPermissionForSendSmsLauncher?.let { launcher ->
            accessPermission(launcher, Manifest.permission.SEND_SMS) {
                viewModel.onSendSmsPermissionResponse(true)
            }
        }
    }

    private fun clearEditTextAndSetEditorAction() {
        binding?.input?.onEditorAction(EditorInfo.IME_ACTION_SEND)
        binding?.input?.text?.clear()
    }

    override fun updateColor(colorState: ColorState) {
        super.updateColor(colorState)
        val color: Int = getColor(colorState.colorResId)
        binding?.buttonSend?.updateColor(color)
    }

    override fun onDestroy() {
        binding = null
        requestPermissionForSendSmsLauncher = null
        super.onDestroy()
    }
}
