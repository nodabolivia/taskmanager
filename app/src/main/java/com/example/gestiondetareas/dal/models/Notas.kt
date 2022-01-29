package com.example.gestiondetareas.dal.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "notas_table")
data class Notas (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "titulo") val titulo:String,
    @ColumnInfo(name = "texto" ,defaultValue = "" ) val texto:String?,
    @ColumnInfo(name = "tipo",defaultValue = "texto" )  val tipo:String,
    @ColumnInfo(name= "color", defaultValue = "color_one") val color:String
    ):Parcelable