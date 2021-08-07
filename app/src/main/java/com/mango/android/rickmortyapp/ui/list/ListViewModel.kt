package com.mango.android.rickmortyapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mango.android.domain.entity.CharacterEntity
import com.mango.android.domain.entity.CharacterQueryEntity
import com.mango.android.domain.interactor.Failure
import com.mango.android.domain.interactor.OneOf
import com.mango.android.domain.usecase.GetCharacters
import com.mango.android.domain.usecase.GetCharactersParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListViewModel @Inject constructor(private val getCharacters: GetCharacters) : ViewModel() {

    private val mCharacters = MutableLiveData<MutableList<CharacterEntity>>(mutableListOf())
    private val mState = MutableLiveData(State.NotInit)
    private val mCurrentPage = MutableLiveData(0)

    val characters: LiveData<MutableList<CharacterEntity>> get() = mCharacters
    val state: LiveData<State> get() = mState
    val currentPage: LiveData<Int> get() = mCurrentPage

    fun loadNextPage() {
        mState.postValue(State.Loading)
        viewModelScope.launch {
            mCurrentPage.value?.let { page ->
                val nextPage = page + 1
                withContext(Dispatchers.IO) {
                    getCharacters.exec(GetCharactersParams(nextPage)) { result ->
                        when (result) {
                            is OneOf.Error -> processError(result.error)
                            is OneOf.Success -> processResult(result.data)
                        }
                    }
                }
            }
        }
    }

    fun reset() {
        mCurrentPage.postValue(0)
        mCharacters.value?.clear()
        mCharacters.postValue(mCharacters.value)
        mState.postValue(State.NotInit)
    }

    private fun processResult(data: CharacterQueryEntity) {
        viewModelScope.launch {
            mCharacters.value?.addAll(data.data)
            mCharacters.postValue(mCharacters.value)

            mCurrentPage.value?.let { value ->
                mCurrentPage.postValue(value + 1)

                if (value >= data.pages)
                    mState.postValue(State.LoadedEndOfData)
                else
                    mState.postValue(State.Loaded)
            }
        }
    }

    private fun processError(error: Failure) {
        when (error) {
            Failure.NetworkFailure -> mState.postValue(State.ErrorLoading)
            Failure.UnknownFailure, Failure.OutOfBoundsFailure -> mState.postValue(State.LoadedEndOfData)
        }
    }

    enum class State {
        NotInit, Loading, Loaded, LoadedEndOfData, ErrorLoading
    }
}