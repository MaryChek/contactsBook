package com.example.ft_hangouts.presentation.fragments

import android.Manifest
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import com.example.ft_hangouts.R
import com.example.ft_hangouts.databinding.FragmentContactsBookBinding
import com.example.ft_hangouts.getColor
import com.example.ft_hangouts.presentation.adapters.ContactsListAdapter
import com.example.ft_hangouts.presentation.fragments.base.BaseViewModelFragment
import com.example.ft_hangouts.domain.models.ColorState
import com.example.ft_hangouts.presentation.activities.MainActivity
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.navigation.FromContactsBook
import com.example.ft_hangouts.presentation.navigation.router.ContactsBookRouter
import com.example.ft_hangouts.presentation.viewmodels.ContactsBookViewModel
import com.example.ft_hangouts.updateColor

class ContactsBookFragment : BaseViewModelFragment<
        List<Contact>, FromContactsBook, FromContactsBook.Navigate, ContactsBookRouter,
        ContactsBookViewModel>(), RegistrationActivityResult {

    private var binding: FragmentContactsBookBinding? = null
    private var adapter: ContactsListAdapter? = null
    private var requestPermissionForGetSmsLauncher: ActivityResultLauncher<String>? = null
    private var requestPermissionForCallPhoneLauncher: ActivityResultLauncher<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityResultLaunchers()
    }

    private fun initActivityResultLaunchers() {
        requestPermissionForGetSmsLauncher = registerForRequestPermissionResult()
        requestPermissionForCallPhoneLauncher =
            registerForRequestPermissionResult(viewModel::onCallPhonePermissionResponse)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBookBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonCreateNewContactClickListener()
        initContactList()
    }

    private fun initButtonCreateNewContactClickListener() {
        binding?.buttonCreateNewContact?.setOnClickListener { viewModel.onButtonCreateNewContactClick() }
    }

    private fun initContactList() {
        val color = getColor(R.color.colorPurple)
        adapter = ContactsListAdapter(
            viewModel::onIconChatClick,
            viewModel::onImgContactClick,
            viewModel::onCallClick,
            color
        )
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
            is FromContactsBook.Command.AccessCallPhonePermissions -> accessCallPhonePermissions()
            is FromContactsBook.Command.CallPhone -> callNumber(destination.number)
            is FromContactsBook.Navigate -> router.goToScreen(destination)
        }
    }

    override fun updateScreen(model: List<Contact>) {
        adapter?.submitList(model)
    }

    private fun closeActivity() {
        activity?.finish()
    }

    private fun accessGetSmsPermissions() {
        requestPermissionForGetSmsLauncher?.let { launcher ->
            accessPermission(launcher, Manifest.permission.RECEIVE_SMS)
        }
    }

    private fun accessCallPhonePermissions() {
        requestPermissionForCallPhoneLauncher?.let { launcher ->
            accessPermission(launcher, Manifest.permission.CALL_PHONE) {
                viewModel.onCallPhonePermissionResponse(true)
            }
        }
    }

    private fun callNumber(number: String) =
        (activity as MainActivity).callNumber(number)

    override fun updateColor(colorState: ColorState) {
        super.updateColor(colorState)
        val color = getColor(colorState.colorResId)
        binding?.buttonCreateNewContact?.updateColor(color)
        adapter?.updateColor(color)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}