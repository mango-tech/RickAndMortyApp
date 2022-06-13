package com.mango.android.rickmortyapp.ui.viewmodel.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import es.andres.bailen.domain.models.CharacterModel
import es.andres.bailen.domain.models.DataResult
import es.andres.bailen.domain.usecases.CharacterListUseCase
import kotlinx.coroutines.flow.Flow

class ListViewModel(private val useCase: CharacterListUseCase): ViewModel() {

    val characterFlow: Flow<PagingData<CharacterModel>>
        get() = _characterFlow

    private var _characterFlow: Flow<PagingData<CharacterModel>> = useCase.getCharacterListPaged().cachedIn(viewModelScope)
}