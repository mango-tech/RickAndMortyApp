package es.andres.bailen.data.network.model

import com.google.gson.annotations.SerializedName

data class CharacterDataModel(
    var id: Int = 0,
    var name: String? = null,
    var status: Status? = null,
    var species: String? = null,
    var type: String? = null,
    var gender: Gender? = null,
    var image: String? = null

) {
    enum class Gender {
        @SerializedName("unknown")
        UNKNOWN,

        @SerializedName("Female")
        FEMALE,

        @SerializedName("Male")
        MALE,

        @SerializedName("Genderless")
        GENDERLESS
    }

    enum class Status {
        @SerializedName("unknown")
        UNKNOWN,
        @SerializedName("Alive")
        ALIVE,
        @SerializedName("Dead")
        DEAD
    }

}

