package com.example.ft_hangouts.presentation.fragments.base

import android.widget.EditText
import android.widget.TextView
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation
import com.example.ft_hangouts.presentation.navigation.base.GoToScreen
import com.example.ft_hangouts.presentation.viewmodels.base.BaseViewModel

abstract class BaseContactWithEditTextFragment<
        Model : Any,
        FromScreen : BaseNavigation,
        Navigate: BaseNavigation.Navigate,
        Router : GoToScreen<Navigate>,
        ViewModel : BaseViewModel<Model, FromScreen>>
    : BaseViewModelFragment<Model, FromScreen, Navigate, Router, ViewModel>() {

    protected fun EditText.setOnTextSubmitListener(onTextSubmitCallback: (String) -> Unit) {
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
}