package com.example.ft_hangouts.presentation.fragments.base

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation
import com.example.ft_hangouts.presentation.navigation.base.GoToScreen
import com.example.ft_hangouts.presentation.viewmodels.base.BaseViewModel

abstract class BaseContactWithEditTextFragment<
        Model : Any,
        FromScreen : BaseNavigation,
        Navigate : BaseNavigation.Navigate,
        Router : GoToScreen<Navigate>,
        ViewModel : BaseViewModel<Model, FromScreen>>
    : BaseViewModelFragment<Model, FromScreen, Navigate, Router, ViewModel>() {

    protected fun EditText.setOnTextSubmitListener(
        onTextSubmitCallback: (String) -> Unit,
        shouldDoBasicActionByActionId: ((Int) -> Boolean)? = null,
        enableAction: Boolean = true,
        enableFocus: Boolean = true,
    ) {
        if (enableAction) setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->
                onTextSubmitCallback(this.text.toString())
                return@OnEditorActionListener shouldDoBasicActionByActionId?.invoke(actionId)?.not() ?: false
            },
        )
        if (enableFocus) {
            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    onTextSubmitCallback(this.text.toString())
                }
            }
        }
    }

    protected fun hideKeyboard() {
        val inputManager: InputMethodManager? =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputManager?.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }
}