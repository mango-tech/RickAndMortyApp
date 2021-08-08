package com.mango.android.domain.usecase

import com.mango.android.domain.entity.CharacterQueryEntity
import com.mango.android.domain.interactor.Failure
import com.mango.android.domain.interactor.OneOf
import com.mango.android.domain.interactor.UseCase
import com.mango.android.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacters @Inject constructor(private val repository: CharacterRepository) :
    UseCase<CharacterQueryEntity, GetCharactersParams> {

    override suspend fun exec(
        params: GetCharactersParams,
        onResult: (OneOf<Failure, CharacterQueryEntity>) -> Unit
    ) {
        onResult(repository.getCharacters(params.page))
    }
}

class GetCharactersParams(val page: Int = 1)