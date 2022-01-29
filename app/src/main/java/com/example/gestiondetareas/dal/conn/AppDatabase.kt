package com.example.gestiondetareas.dal.conn

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gestiondetareas.dal.bll.CasillasDao
import com.example.gestiondetareas.dal.bll.NotasDao
import com.example.gestiondetareas.dal.models.Casillas
import com.example.gestiondetareas.dal.models.Notas

@Database(entities = [Notas::class, Casillas::class ], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notasDao(): NotasDao
    abstract fun casillasDao(): CasillasDao
    companion object{
        private var INSTANCE : AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            if(INSTANCE==null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "gestortareas"
                ).allowMainThreadQueries()
                    .build()
            }
            return  INSTANCE!!
        }
    }
}