package com.example.ft_hangouts.presentation.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat.checkSelfPermission
import com.example.ft_hangouts.presentation.fragments.base.BaseContactEditorFragment
import com.example.ft_hangouts.presentation.models.ContactState
import com.example.ft_hangouts.presentation.navigation.FromContactCreator
import com.example.ft_hangouts.presentation.navigation.router.ContactCreatorRouter
import com.example.ft_hangouts.presentation.viewmodels.ContactCreatorViewModel
import java.lang.IllegalStateException

class ContactCreatorFragment : BaseContactEditorFragment<
        ContactState, FromContactCreator, FromContactCreator.Navigate,
        ContactCreatorRouter, ContactCreatorViewModel>(), RegistrationActivityResult {

    private val logTag: String = this::class.java.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
    }

    override fun getViewModelClass(): Class<ContactCreatorViewModel> =
        ContactCreatorViewModel::class.java

    override fun getNavRouter(): ContactCreatorRouter =
        ContactCreatorRouter(navController)

    override fun navigateTo(destination: FromContactCreator) =
        when (destination) {
            is FromContactCreator.Command.ShowErrorMessage ->
                showErrorMessage(destination.errorMessageResId)
            is FromContactCreator.Command.AccessWritePermissions ->
                accessWritePermissions()
            is FromContactCreator.Command.AccessReadPermissions ->
                accessReadPermissions()
            is FromContactCreator.Command.TakePictureFromGallery ->
                takePicture()
            is FromContactCreator.Navigate ->
                router.goToScreen(destination)
        }

    private fun takePicture() {
        getContentLauncher?.launch(IMAGES_DIRECTORY)
            ?: Log.e(logTag, "get content launcher not init", IllegalStateException())
    }

    private fun accessReadPermissions() {
        requestPermissionForReadStorageLauncher?.let { launcher ->
            accessPermission(
                launcher,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                viewModel::onReadStoragePermissionIsGranted,
            )
        } ?: Log.e(logTag, "permission for read launcher not init", IllegalStateException())
    }
//        accessPermission(
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            viewModel::onReadStoragePermissionIsGranted,
//            viewModel::onReadStoragePermissionIsNotGranted
//        )
//        if (isPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            startPermissionRequest(
//                this,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                viewModel::onReadStoragePermissionIsGranted,
//                viewModel::onReadStoragePermissionIsNotGranted
//            )
//        }
//    }

    private fun accessWritePermissions() {
        requestPermissionForWriteStorageLauncher?.let { launcher ->
            accessPermission(
                launcher,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                viewModel::onWriteStoragePermissionIsGranted,
            )
        } ?: Log.e(logTag, "permission for write launcher not init", IllegalStateException())
    }

//        if (isPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            startPermissionRequest(
//                this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                viewModel::onWriteStoragePermissionIsGranted,
//                viewModel::onWriteStoragePermissionIsNotGranted
//            )
//        }
//    }

    private fun accessPermission(
        permissionLauncher: ActivityResultLauncher<String>,
        permission: String,
        onPermissionIsGranted: () -> Unit,
    ) {
        try {
            if (isPermissionDenied(permission)) {
                permissionLauncher.launch(permission)
            } else {
                onPermissionIsGranted()
            }
        } catch (e: IllegalStateException) {
            Log.e(logTag, "Context is null", e)
            //TODO show something wrong try again
        }
    }

    private fun isPermissionDenied(permission: String): Boolean =
        checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_DENIED

    companion object {
        private const val IMAGES_DIRECTORY = "image/*"
    }
}