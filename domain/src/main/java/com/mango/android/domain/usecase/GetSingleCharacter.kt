package com.mango.android.domain.usecase

import com.mango.android.domain.entity.CharacterEntity
import com.mango.android.domain.interactor.Failure
import com.mango.android.domain.interactor.OneOf
import com.mango.android.domain.interactor.UseCase
import com.mango.android.domain.repository.CharacterRepository

class GetSingleCharacter(private val repository: CharacterRepository) :
    UseCase<CharacterEntity, GetSingleCharacterParams> {
    override suspend fun exec(
        params: GetSingleCharacterParams,
        onResult: (OneOf<Failure, CharacterEntity>) -> Unit
    ) {
        try {
            val data = repository.getCharacter(params.id)
            onResult(OneOf.Success(data))
        } catch (e: Exception) {
            // Type should come from repository
            onResult(OneOf.Error(Failure.UnknownFailure))
        }
    }
}

class GetSingleCharacterParams(val id: Int)