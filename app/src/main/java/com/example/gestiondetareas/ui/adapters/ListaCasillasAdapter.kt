package com.example.gestiondetareas.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.gestiondetareas.dal.models.Casillas
import com.example.gestiondetareas.databinding.ItemListCasillasBinding

class ListaCasillasAdapter(
    var listaCasillas: ArrayList<Casillas>,
    var listener: CasillaAdapterListener
) :
    RecyclerView.Adapter<CasillaViewHolder>() {
    private val viewBindingHelper = ViewBinderHelper()
    private val viewBinderHelper = ViewBinderHelper()
    private lateinit var currentCasilla: Casillas
    init {
        viewBinderHelper.setOpenOnlyOne(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CasillaViewHolder {
        return CasillaViewHolder(
            ItemListCasillasBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CasillaViewHolder, position: Int) {
       currentCasilla = listaCasillas[position]

       holder.binding.cbNombreCas.text = "${currentCasilla.texto}"
       holder.binding.txtNombreCasUpdate.setText("${currentCasilla.texto}")
       holder.binding.cbNombreCas.isChecked = currentCasilla.estado


        holder.binding.btnGuardarCasUpdate.setOnClickListener {
            val id = currentCasilla.id
            val texto = holder.binding.txtNombreCasUpdate.text.toString()
            val estado = holder.binding.cbNombreCas.isChecked
            val idNota= currentCasilla.idNota
            val casillas = Casillas(id,texto,estado,idNota)
           listener.onEditCasilla(casillas)
            holder.binding.cbNombreCas.text = "${texto}"
            holder.binding.itemCasilla.close(true)

        }
        holder.binding.cbNombreCas.setOnCheckedChangeListener { compoundButton, b ->
            val id = currentCasilla.id
            val texto = holder.binding.cbNombreCas.text.toString()
            val estado = holder.binding.cbNombreCas.isChecked
            val idNota= currentCasilla.idNota
            val casillas = Casillas(id = id,texto =texto,estado= estado, idNota = idNota)
            listener.onEditCasilla(casillas)
        }

        holder.binding.btnEliminarCas.setOnClickListener {
            listener.onDeleteCasillas(currentCasilla)
        }


    }

    override fun getItemCount(): Int {
        return this.listaCasillas.size
    }
    fun setData(listaCasillas: ArrayList<Casillas>){
        this.listaCasillas = listaCasillas
    }
    fun deleteItem(casillas: Casillas){
        var deletePosition = 0
        for (objCasilla in listaCasillas){
            if (objCasilla == casillas ){
                listaCasillas.remove(objCasilla)
                break
            }
            deletePosition++
        }
        this.notifyItemRemoved(deletePosition)
    }
}

class CasillaViewHolder( val binding : ItemListCasillasBinding) :
    RecyclerView.ViewHolder(binding.root) {

}
interface  CasillaAdapterListener{
    fun onEditCasilla(casillas: Casillas)
    fun onDeleteCasillas(casillas: Casillas)
}