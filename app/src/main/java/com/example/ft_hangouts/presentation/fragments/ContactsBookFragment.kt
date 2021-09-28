package com.example.ft_hangouts.presentation.fragments

import android.Manifest
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import com.example.ft_hangouts.R
import com.example.ft_hangouts.databinding.FragmentContactsBookBinding
import com.example.ft_hangouts.presentation.adapters.ContactsListAdapter
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.fragments.base.BaseViewModelFragment
import com.example.ft_hangouts.presentation.navigation.FromContactsBook
import com.example.ft_hangouts.presentation.navigation.router.ContactsBookRouter
import com.example.ft_hangouts.presentation.viewmodels.ContactsBookViewModel

class ContactsBookFragment : BaseViewModelFragment<
        List<Contact>, FromContactsBook, FromContactsBook.Navigate, ContactsBookRouter,
        ContactsBookViewModel>(), RegistrationActivityResult {

    private var binding: FragmentContactsBookBinding? = null
    private var adapter: ContactsListAdapter? = null
    private var requestPermissionForGetSmsLauncher: ActivityResultLauncher<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityResultLaunchers()
    }

    private fun initActivityResultLaunchers() {
        requestPermissionForGetSmsLauncher =
            registerForRequestPermissionResult(viewModel::onGetSmsPermissionResponse)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBookBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.changeColor -> true //TODO open dialog change color
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonCreateNewContactClickListener()
        initContactList()
        viewModel.init()
    }

    private fun initButtonCreateNewContactClickListener() {
        binding?.buttonCreateNewContact?.setOnClickListener { viewModel.onButtonCreateNewContactClick() }
    }

    private fun initContactList() {
        adapter = ContactsListAdapter(viewModel::onIconChatClick, viewModel::onImgContactClick)
        binding?.rvContacts?.adapter = adapter
    }

    override fun getViewModelClass(): Class<ContactsBookViewModel> =
        ContactsBookViewModel::class.java

    override fun getNavRouter(): ContactsBookRouter =
        ContactsBookRouter(navController)

    override fun navigateTo(destination: FromContactsBook) {
        when (destination) {
            is FromContactsBook.Command.CloseActivity -> closeActivity()
            is FromContactsBook.Command.AccessGetSmsPermissions -> accessGetSmsPermissions()
            is FromContactsBook.Navigate -> router.goToScreen(destination)
        }
    }

    override fun updateScreen(model: List<Contact>) {
        adapter?.submitList(model)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        requestPermissionForGetSmsLauncher = null
    }

    private fun closeActivity() {
        activity?.finish()
    }

    private fun accessGetSmsPermissions() {
        requestPermissionForGetSmsLauncher?.let { launcher ->
            accessPermission(launcher, Manifest.permission.RECEIVE_SMS) {
                viewModel.onGetSmsPermissionResponse(true)
            }
        }
    }
}