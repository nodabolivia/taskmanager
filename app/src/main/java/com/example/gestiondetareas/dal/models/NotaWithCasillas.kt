package com.example.gestiondetareas.dal.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotaWithCasillas(
    @Embedded val nota: Notas,
    @Relation(
        parentColumn = "id",
        entityColumn = "idNota"
    )
    val casillas:List<Casillas>
):Parcelable

