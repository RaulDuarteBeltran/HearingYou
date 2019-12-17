package com.redb.hearingyou.Modelos.Firebase

data class PsicologoFB (
    var correo:String="",
    var fechaNaciemiento:String="",
    var nombre:String="",
    var conversaciones:MutableMap<String,Boolean> = HashMap(),
    var usuariosFavoritos:MutableMap<String,Boolean> = HashMap()
){

    var id:String?=null
}