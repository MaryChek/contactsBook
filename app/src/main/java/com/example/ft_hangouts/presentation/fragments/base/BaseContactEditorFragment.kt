package com.example.ft_hangouts.presentation.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import com.example.ft_hangouts.databinding.FragmentContactEditorBinding
import com.example.ft_hangouts.presentation.fragments.RegistrationActivityResult
import com.example.ft_hangouts.presentation.models.ContactState
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation
import com.example.ft_hangouts.presentation.navigation.router.BaseRouter
import com.example.ft_hangouts.presentation.viewmodels.base.BaseContactEditorViewModel

abstract class BaseContactEditorFragment<
        FromScreen : BaseNavigation,
        Navigate : BaseNavigation.Navigate,
        Router : BaseRouter<Navigate>,
        ViewModel : BaseContactEditorViewModel<FromScreen>>
    : BaseViewModelFragment<ContactState, FromScreen, Navigate, Router, ViewModel>(),
    RegistrationActivityResult {

    protected var binding: FragmentContactEditorBinding? = null

    protected var requestPermissionForWriteStorageLauncher: ActivityResultLauncher<String>? = null
    protected var requestPermissionForReadStorageLauncher: ActivityResultLauncher<String>? = null
    protected var getContentLauncher: ActivityResultLauncher<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityResultLaunchers()
    }

    private fun initActivityResultLaunchers() {
        requestPermissionForReadStorageLauncher = this.registerForRequestPermissionResult(
            viewModel::onReadStoragePermissionIsGranted,
            viewModel::onReadStoragePermissionIsNotGranted
        )

        requestPermissionForWriteStorageLauncher = this.registerForRequestPermissionResult(
            viewModel::onWriteStoragePermissionIsGranted,
            viewModel::onWriteStoragePermissionIsNotGranted
        )

        getContentLauncher = this.registerForGettingContentResult(viewModel::onTakePicture)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactEditorBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonClickListeners()
        initEditTextSubmitListeners()
    }

    private fun initButtonClickListeners() {
        binding?.buttonSave?.setOnClickListener {
            clearEditTextsFocus()
            viewModel.onSaveContactClick()
        }
        binding?.buttonAddPhoto?.setOnClickListener { viewModel.onAddPhotoClick() }
    }

    private fun clearEditTextsFocus() {
        binding?.edtName?.clearFocus()
        binding?.edtLastName?.clearFocus()
        binding?.edtNumber?.clearFocus()
        binding?.edtEmail?.clearFocus()
    }

    private fun initEditTextSubmitListeners() {
        binding?.edtName?.setOnTextSubmitListener(viewModel::onNameSubmit)
        binding?.edtLastName?.setOnTextSubmitListener(viewModel::onLastNameSubmit)
        binding?.edtNumber?.setOnTextSubmitListener(viewModel::onNumberSubmit)
        binding?.edtEmail?.setOnTextSubmitListener(viewModel::onEmailSubmit)
    }

    private fun EditText.setOnTextSubmitListener(onTextSubmitCallback: (String) -> Unit) {
        setOnEditorActionListener(TextView.OnEditorActionListener { _, _, _ ->
            onTextSubmitCallback(this.text.toString())
            return@OnEditorActionListener false
        })
        setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                onTextSubmitCallback(this.text.toString())
            }
        }
    }

    protected fun showErrorMessage(@StringRes errorMessageResId: Int) {
        val message: String = getString(errorMessageResId)
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        requestPermissionForWriteStorageLauncher = null
        requestPermissionForReadStorageLauncher = null
        getContentLauncher = null
    }
}