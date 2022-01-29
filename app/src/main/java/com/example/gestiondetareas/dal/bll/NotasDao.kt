package com.example.gestiondetareas.dal.bll

import androidx.room.*
import com.example.gestiondetareas.dal.models.NotaWithCasillas
import com.example.gestiondetareas.dal.models.Notas

@Dao
interface NotasDao {
    @Query("SELECT * FROM notas_table")
    fun getAll():List<Notas>

    @Query("SELECT * FROM notas_table WHERE id = :id")
    fun getById(id:Int): Notas

    @Insert
    fun insert(vararg  nota: Notas)

    @Update
    fun update(nota: Notas)

    @Delete
    fun delete(nota: Notas)

    @Transaction
    @Query("SELECT * FROM notas_table")
    fun getNotasWithCasillas():List<NotaWithCasillas>

}