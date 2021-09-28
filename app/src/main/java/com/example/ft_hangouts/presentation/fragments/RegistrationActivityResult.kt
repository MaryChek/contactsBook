package com.example.ft_hangouts.presentation.fragments

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

interface RegistrationActivityResult {

    fun Fragment.registerForRequestPermissionResult(
        onResponse: (Boolean) -> Unit,
    ): ActivityResultLauncher<String> =
        this.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            onResponse(isGranted)
        }

    fun Fragment.registerForGettingContentResult(
        onSuccessCallBack: (Uri) -> Unit,
    ): ActivityResultLauncher<String> =
        this.registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri ->
            onSuccessCallBack(uri)
        }



//    fun startPermissionRequest(
//        fragment: Fragment,
//        permission: String,
//        onIsGrantedCallBack: () -> Unit,
//        onIsNotGrantedCallBack: () -> Unit,
//    ) {
//        val requestPermissionLauncher: ActivityResultLauncher<String> =
//            fragment.registerForActivityResult(
//                ActivityResultContracts.RequestPermission()
//            ) { isGranted ->
//                when (isGranted) {
//                    true -> onIsGrantedCallBack()
//                    false -> onIsNotGrantedCallBack()
//                }
//            }
//        requestPermissionLauncher.launch(permission)
//    }

//    fun takeContentFrom(
//        contentDir: String,
//        fragment: Fragment,
//        onSuccessCallBack: (Uri) -> Unit,
//    ) {
//        val requestPermissionLauncher: ActivityResultLauncher<String> =
//            fragment.registerForActivityResult(
//                ActivityResultContracts.GetContent()
//            ) { uri: Uri ->
//                onSuccessCallBack(uri)
//            }
//        requestPermissionLauncher.launch(contentDir)
//    }
}