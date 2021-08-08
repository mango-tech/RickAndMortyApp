package com.mango.android.rickmortyapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mango.android.domain.entity.CharacterEntity
import com.mango.android.domain.interactor.OneOf
import com.mango.android.domain.usecase.GetSingleCharacter
import com.mango.android.domain.usecase.GetSingleCharacterParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val getCharacter: GetSingleCharacter) :
    ViewModel() {

    private val mCharacter = MutableLiveData<CharacterEntity>()
    private val mState = MutableLiveData(State.NotInit)

    fun character(): LiveData<CharacterEntity> {
        return mCharacter
    }

    fun state(): LiveData<State> {
        return mState
    }

    fun loadCharacter(id: Int){
        mState.postValue(State.Loading)
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                getCharacter.exec(GetSingleCharacterParams(id)){
                    when(it){
                        is OneOf.Error -> processError()
                        is OneOf.Success -> processSuccess(it.data)
                    }
                }
            }
        }
    }

    private fun processSuccess(data: CharacterEntity) {
        viewModelScope.launch {
            mCharacter.postValue(data)
            mState.postValue(State.Loaded)
        }
    }

    private fun processError() {
        mState.postValue(State.Error)
    }

    enum class State {
        NotInit, Loading, Loaded, Error
    }
}