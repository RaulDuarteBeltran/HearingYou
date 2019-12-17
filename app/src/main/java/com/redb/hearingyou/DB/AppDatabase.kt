package com.redb.hearingyou.DB

import android.content.Context
import androidx.room.Database
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import com.redb.hearingyou.DB.DAOs.AplicacionDao
import com.redb.hearingyou.DB.Entidades.AplicacionEntity

@Database(
    entities =[
        AplicacionEntity::class
    ],
    version = 1
)

abstract class AppDatabase:RoomDatabase(){

    //Instancias de los Daos

    abstract fun getAplicacionDao():AplicacionDao

    //Objeto singleton
    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase::class.java,
                    "HearingYou.db"
                )
                    .allowMainThreadQueries()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            initializeData(db)
                        }
                    })
                    .build()
            }

            return INSTANCE as AppDatabase
        }

        fun initializeData(db: SupportSQLiteDatabase) {
            db.beginTransaction()

            db.execSQL("INSERT INTO Aplicacion(idAplicacion) VALUES (0)")

            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }
}