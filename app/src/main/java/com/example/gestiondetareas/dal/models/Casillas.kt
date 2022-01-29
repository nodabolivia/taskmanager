package com.example.gestiondetareas.dal.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "casillas_table")
data class Casillas (
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "texto") val texto:String,
    @ColumnInfo(name = "estado") val estado:Boolean,
    @ColumnInfo(name = "idNota") val idNota:Int
    ):Parcelable