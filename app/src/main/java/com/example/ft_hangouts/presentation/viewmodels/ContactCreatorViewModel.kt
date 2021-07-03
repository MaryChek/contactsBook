package com.example.ft_hangouts.presentation.viewmodels

import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.models.ContactState
import com.example.ft_hangouts.presentation.navigation.FromContactCreator
import com.example.ft_hangouts.presentation.viewmodels.base.BaseContactEditorViewModel

class ContactCreatorViewModel(interactor: ContactsInteractor) :
    BaseContactEditorViewModel<FromContactCreator>(interactor) {
    override fun onAddPhotoClick() {
        //TODO спросить доступ к галерее и поставить фото
    }

    override fun showMessageError() =
        goToScreen(FromContactCreator.ShowErrorMessage(model.errorMessageResId))

    override fun goToPrevious() =
        goToScreen(FromContactCreator.PreviousScreen)
}