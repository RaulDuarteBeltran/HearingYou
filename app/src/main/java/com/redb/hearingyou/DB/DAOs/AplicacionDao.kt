package com.redb.hearingyou.DB.DAOs

import androidx.room.Dao
import androidx.room.Query
import com.redb.hearingyou.DB.Entidades.AplicacionEntity

@Dao
interface AplicacionDao {
    @Query("SELECT * FROM Aplicacion WHERE idAplicacion=0")
    fun getAplicacion():AplicacionEntity

    @Query("UPDATE Aplicacion SET idUser=:idUser")
    fun logInUser(idUser:String)

    @Query("UPDATE Aplicacion SET userName=:userName")
    fun setUserName(userName:String)

    @Query("UPDATE Aplicacion SET idUser=null, userName=null")
    fun logOutUser()
}