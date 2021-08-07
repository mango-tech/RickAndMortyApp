package com.mango.android.data.net.mapper

import com.mango.android.data.net.model.CharacterResult
import com.mango.android.domain.entity.CharacterEntity

fun CharacterResult.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(id, name, status, species, type, gender, image)
}