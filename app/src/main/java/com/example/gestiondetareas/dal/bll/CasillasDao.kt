package com.example.gestiondetareas.dal.bll

import androidx.room.*
import com.example.gestiondetareas.dal.models.Casillas

@Dao
interface CasillasDao {
    @Query("SELECT * FROM casillas_table")
    fun getAll():List<Casillas>

    @Query("SELECT * FROM casillas_table WHERE idNota= :idNota")
    fun getAllByNota(idNota: Int):List<Casillas>

    @Query("SELECT * FROM casillas_table WHERE id = :id")
    fun getById(id:Int): Casillas

    @Insert
    fun insert(vararg  casilla: Casillas)

    @Update
    fun update(casilla: Casillas)

    @Delete
    fun delete(casilla: Casillas)

}