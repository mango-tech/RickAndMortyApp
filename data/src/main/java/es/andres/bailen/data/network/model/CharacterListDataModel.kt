package es.andres.bailen.data.network.model

data class CharacterListDataModel(
    val info: CharacterListInfo,
    val results: List<CharacterDataModel>
)

data class CharacterListInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
