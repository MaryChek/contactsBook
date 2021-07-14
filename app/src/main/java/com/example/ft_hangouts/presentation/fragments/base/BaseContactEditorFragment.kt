package com.example.ft_hangouts.presentation.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import com.example.ft_hangouts.databinding.FragmentContactEditorBinding
import com.example.ft_hangouts.presentation.models.ContactState
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation
import com.example.ft_hangouts.presentation.navigation.router.BaseRouter
import com.example.ft_hangouts.presentation.viewmodels.base.BaseContactEditorViewModel

abstract class BaseContactEditorFragment<
        Model: ContactState,
        FromScreen : BaseNavigation,
        Router : BaseRouter<FromScreen>,
        ViewModel: BaseContactEditorViewModel<Model, FromScreen>>
    : BaseViewModelFragment<Model, FromScreen, Router, ViewModel>() {

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

    private fun initButtonClickListeners() {
        binding?.buttonSave?.setOnClickListener { viewModel.onSaveContactClick() }
        binding?.buttonAddPhoto?.setOnClickListener { viewModel.onAddPhotoClick() }
    }

    private fun initEditTextSubmitListeners() {
        binding?.edtName?.setOnTextSubmitListener(viewModel::onNameSubmit)
        binding?.edtNumber?.setOnTextSubmitListener(viewModel::onNumberSubmit)
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
    }
}