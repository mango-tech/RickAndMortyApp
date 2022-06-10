package com.mango.android.rickmortyapp.ui.viewmodel.details

import androidx.lifecycle.ViewModel
import es.andres.bailen.domain.models.CharacterModel
import es.andres.bailen.domain.usecases.CharacterListUseCase

class DetailViewModel(private val characterListUseCase: CharacterListUseCase): ViewModel() {

    fun getCharacter(characterId: String): CharacterModel? {
        return characterListUseCase.getCharacterDetail(characterId)
    }
}