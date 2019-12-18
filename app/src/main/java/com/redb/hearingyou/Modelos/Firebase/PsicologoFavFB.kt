package com.redb.hearingyou.Modelos.Firebase

data class PsicologoFavFB (
    var nombre:String ="",
    var conectado:Boolean=true,
    var idConversacion:String? = null
) {
    var id:String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PsicologoFavFB

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}