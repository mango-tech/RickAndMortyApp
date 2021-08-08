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
        onResult(repository.getCharacter(params.id))
    }
}

class GetSingleCharacterParams(val id: Int)