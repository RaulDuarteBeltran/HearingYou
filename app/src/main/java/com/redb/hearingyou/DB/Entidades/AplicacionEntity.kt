package com.redb.hearingyou.DB.Entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Aplicacion")
data class AplicacionEntity (
    @PrimaryKey @ColumnInfo(name = "idAplicacion") val idAplicacion:Int,
    @ColumnInfo(name = "idUser") var idUser:String?= null,
    @ColumnInfo(name = "userName") var userName:String?=null
)