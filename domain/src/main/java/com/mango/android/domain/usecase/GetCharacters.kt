package com.mango.android.domain.usecase

import com.mango.android.domain.entity.CharacterQueryEntity
import com.mango.android.domain.interactor.Failure
import com.mango.android.domain.interactor.OneOf
import com.mango.android.domain.interactor.UseCase
import com.mango.android.domain.repository.CharacterRepository
import java.io.IOException

class GetCharacters(private val repository: CharacterRepository) :
    UseCase<CharacterQueryEntity, GetCharactersParams> {

    override suspend fun exec(
        params: GetCharactersParams,
        onResult: (OneOf<Failure, CharacterQueryEntity>) -> Unit
    ) {
        try {
            val data = repository.getCharacters(params.page)
            onResult(OneOf.Success(data))
        } catch (e: IOException) {
            onResult(OneOf.Error(Failure.NetworkFailure))
        } catch (e: Exception) {
            // Type should come from repository
            onResult(OneOf.Error(Failure.UnknownFailure))
        }
    }
}

class GetCharactersParams(val page: Int = 1)