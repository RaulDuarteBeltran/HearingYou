package com.redb.hearingyou.Modelos.Firebase

data class UsuarioFB(
    var correo:String="",
    var contraseña:String="",
    var tipo:Int=0,
    var validado:Boolean=false
){
    var id:String?=null
}