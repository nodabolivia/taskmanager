package com.example.gestiondetareas.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.gestiondetareas.R

class SquareComponent (context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    init{
        val inflater= context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.square_color,this)
    }
}