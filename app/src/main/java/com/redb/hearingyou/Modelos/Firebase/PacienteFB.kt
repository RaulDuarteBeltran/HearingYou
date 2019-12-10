package com.redb.hearingyou.Modelos.Firebase

data class PacienteFB (
    var sobrenombre:String="",
    var nombre:String="",
    var correo:String="",
    var fechaNacimiento:String="",
    var conversaciones:MutableMap<String,Boolean> = HashMap()
)
{
    var id:String?=null
}