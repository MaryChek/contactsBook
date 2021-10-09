package com.example.ft_hangouts.presentation.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import com.example.ft_hangouts.databinding.FragmentContactEditorBinding
import com.example.ft_hangouts.presentation.models.ContactState
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation
import com.example.ft_hangouts.presentation.navigation.router.BaseRouter
import com.example.ft_hangouts.presentation.viewmodels.base.BaseContactEditorViewModel

abstract class BaseContactEditorFragment<
        FromScreen : BaseNavigation,
        Navigate : BaseNavigation.Navigate,
        Router : BaseRouter<Navigate>,
        ViewModel : BaseContactEditorViewModel<FromScreen>>
    : BaseContactWithEditTextFragment<ContactState, FromScreen, Navigate, Router, ViewModel>() {

    protected var binding: FragmentContactEditorBinding? = null

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

    private fun initButtonClickListeners() =
        binding?.buttonSave?.setOnClickListener {
            clearEditTextsFocus()
            viewModel.onSaveContactClick()
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}