package com.example.ft_hangouts.presentation.viewmodels.base

import android.net.Uri
import android.util.Log
import com.example.ft_hangouts.presentation.models.ContactState
import com.example.ft_hangouts.presentation.navigation.FromContactCreator
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation

abstract class BaseContactEditorViewModel<
        ContactStateModel : ContactState,
        FromScreen : BaseNavigation>(
    contactState: ContactStateModel
) : BaseViewModel<ContactStateModel, FromScreen>(contactState) {

    fun onSaveContactClick() {
        if (model.isContactCorrect) {
            onContactCorrect()
        } else {
            showMessageError()
        }
    }

    abstract fun onContactCorrect()

    abstract fun onAddPhotoClick()

    abstract fun onNameSubmit(name: String)

    abstract fun onNumberSubmit(number: String)

    abstract fun showMessageError()

    open fun onReadStoragePermissionIsGranted() =
        Unit

    open fun onTakePicture(imageUri: Uri) =
        Unit

    open fun onReadStoragePermissionIsNotGranted() =
        Unit

    open fun onWriteStoragePermissionIsGranted() =
        Unit

    open fun onWriteStoragePermissionIsNotGranted() =
        Unit
}