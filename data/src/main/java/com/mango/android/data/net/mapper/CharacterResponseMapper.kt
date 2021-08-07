package com.mango.android.data.net.mapper

import com.mango.android.data.net.model.CharacterResponse
import com.mango.android.domain.entity.CharacterQueryEntity
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform

suspend fun CharacterResponse.toCharacterQueryEntity(): CharacterQueryEntity {
    val data = results
        .asFlow()
        .transform {
            emit(it.toCharacterEntity())
        }.toList()

    return CharacterQueryEntity(
        info.count,
        info.pages,
        data
    )
}