package com.example.ft_hangouts.presentation.fragments

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

interface RegistrationActivityResult {

    fun Fragment.registerForRequestPermissionResult(
        onResponse: ((Boolean) -> Unit)? = null,
    ): ActivityResultLauncher<String> =
        this.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            onResponse?.invoke(isGranted)
        }
}