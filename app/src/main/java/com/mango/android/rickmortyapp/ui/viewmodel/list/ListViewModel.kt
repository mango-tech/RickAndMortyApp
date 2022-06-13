package com.mango.android.rickmortyapp.ui.viewmodel.list

import androidx.lifecycle.ViewModel
import es.andres.bailen.domain.models.CharacterModel
import es.andres.bailen.domain.models.DataResult
import es.andres.bailen.domain.usecases.CharacterListUseCase

class ListViewModel(private val useCase: CharacterListUseCase): ViewModel() {

    suspend fun getCharacterList(): DataResult<List<CharacterModel>> {
        return useCase.getCharacterList()
    }
}