package es.andres.bailen.domain.models

data class CharacterModel(
    var id: Int = 0,
    var name: String? = null,
    var status: String? = null,
    var species: String? = null,
    var type: String? = null,
    var gender: String? = null,
    var image: String? = null
)