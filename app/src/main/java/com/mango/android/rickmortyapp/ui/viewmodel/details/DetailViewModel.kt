package com.mango.android.rickmortyapp.ui.viewmodel.details

import androidx.lifecycle.ViewModel
import es.andres.bailen.domain.models.CharacterModel
import es.andres.bailen.domain.models.DataResult
import es.andres.bailen.domain.usecases.CharacterListUseCase

class DetailViewModel(private val characterListUseCase: CharacterListUseCase): ViewModel() {

    suspend fun getCharacter(characterId: String): DataResult<CharacterModel> {
        return characterListUseCase.getCharacterDetail(characterId)
    }
}