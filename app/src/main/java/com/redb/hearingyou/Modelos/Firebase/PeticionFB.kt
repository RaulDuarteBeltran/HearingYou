package com.redb.hearingyou.Modelos.Firebase

data class PeticionFB (
    var idUsuario:String ="",
    var sobreNombre:String ="",
    var aceptada:Boolean = false,
    var idPsicologo:String =""
)
{
    var id:String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PeticionFB

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}