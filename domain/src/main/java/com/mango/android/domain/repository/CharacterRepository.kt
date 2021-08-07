package com.mango.android.domain.repository

import com.mango.android.domain.entity.CharacterQueryEntity

interface CharacterRepository {

    fun getCharacters(page: Int = 1): CharacterQueryEntity

}