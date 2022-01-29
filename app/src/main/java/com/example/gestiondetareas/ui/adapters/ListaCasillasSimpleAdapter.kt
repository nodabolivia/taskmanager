package com.example.gestiondetareas.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiondetareas.dal.models.Casillas
import com.example.gestiondetareas.databinding.ItemListCasillasSimpleBinding

class ListaCasillasSimpleAdapter(
    var listaCasillas:List<Casillas>
): RecyclerView.Adapter<CasillaSimpleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CasillaSimpleViewHolder {
        return CasillaSimpleViewHolder(
            ItemListCasillasSimpleBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CasillaSimpleViewHolder, position: Int) {
    val objCasilla = listaCasillas[position]
       holder.binding.cbCasSimple.text = "${objCasilla.texto}"
        holder.binding.cbCasSimple.isChecked = objCasilla.estado
    }

    override fun getItemCount(): Int {
        return listaCasillas.size
    }

    fun setData( listaCasillas: List<Casillas>){
        this.listaCasillas = listaCasillas
    }
}

class CasillaSimpleViewHolder( val binding:ItemListCasillasSimpleBinding) :
    RecyclerView.ViewHolder(binding.root) {
}
