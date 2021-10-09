package com.example.ft_hangouts.presentation.viewmodels.base

import android.net.Uri
import android.util.Log
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.models.ContactState
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation
import java.lang.IllegalArgumentException

abstract class BaseContactEditorViewModel<FromScreen : BaseNavigation>(
    override val interactor: ContactsInteractor
) : BaseViewModel<ContactState, FromScreen>(interactor, ContactState()) {

    open val logTag: String = this::class.java.simpleName

    protected fun updateModel(
        contactId: String? = model.contact?.id,
        personName: String? = model.contact?.name,
        personLastName: String? = model.contact?.lastName,
        personNumber: String? = model.contact?.number,
        personEmail: String? = model.contact?.email,
        imagePath: String? = model.contact?.imagePath,
        isNumberIndividual: Boolean = model.isNumberIndividual,
        isNumberCorrect: Boolean = model.isNumberCorrect
    ) {
        if (contactId != null) {
            val contact = Contact(
                id = contactId,
                name = personName,
                lastName = personLastName,
                number = personNumber,
                email = personEmail,
                imagePath = imagePath
            )
            model = ContactState(contact, isNumberIndividual, isNumberCorrect)
        } else {
            Log.e(logTag, "missing contact id", IllegalArgumentException())
        }
    }

    fun onSaveContactClick() {
        if (model.isContactCorrect) {
            onContactCorrect()
        } else {
            showMessageError()
        }
    }

    abstract fun onContactCorrect()

    abstract fun onAddPhotoClick()

    fun onNameSubmit(name: String) =
        updateModel(personName = name)

    fun onLastNameSubmit(lastName: String) =
        updateModel(personLastName = lastName)

    fun onEmailSubmit(email: String) =
        updateModel(personEmail = email)

    fun onNumberSubmit(number: String) {
        val isNumberIndividual: Boolean = isNumberIndividual(number)
        val isNumberCorrect: Boolean = interactor.isNumberValid(number)
        updateModel(
            personNumber = number,
            isNumberIndividual = isNumberIndividual,
            isNumberCorrect = isNumberCorrect
        )
    }

    abstract fun isNumberIndividual(number: String): Boolean

    abstract fun showMessageError()

    open fun onTakePicture(imageUri: Uri) =
        Unit

    open fun onReadStoragePermissionResponse(isGranted: Boolean) =
        Unit

    open fun onWriteStoragePermissionResponse(isGranted: Boolean) =
        Unit
}