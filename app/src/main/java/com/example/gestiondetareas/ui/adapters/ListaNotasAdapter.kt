package com.example.gestiondetareas.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiondetareas.R
import com.example.gestiondetareas.dal.models.Casillas
import com.example.gestiondetareas.dal.models.NotaWithCasillas
import com.example.gestiondetareas.dal.models.Notas
import com.example.gestiondetareas.databinding.ItemListNotasBinding
import com.example.gestiondetareas.ui.fragments.MainFragmentDirections
import kotlin.math.log

class ListaNotasAdapter
    (var listNotaWithCasillas : List<NotaWithCasillas>):
    RecyclerView.Adapter<NotasViewHolder>() {
    private lateinit var adapter: ListaCasillasSimpleAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotasViewHolder {
        return NotasViewHolder(
            ItemListNotasBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: NotasViewHolder, position: Int) {
        val objNotaWithCasillas = listNotaWithCasillas[position]
        Log.println(Log.INFO,"RECYCLER","${position}:${objNotaWithCasillas.nota.titulo} :${objNotaWithCasillas.casillas}")

        holder.binding.lbTituloNota.text = "${objNotaWithCasillas.nota.titulo}"
        setupBackgroundColor(holder= holder,color = objNotaWithCasillas.nota.color)
        if(objNotaWithCasillas.casillas.isEmpty()){
            holder.binding.lbTextoNota.visibility = View.VISIBLE
            holder.binding.lstCasillasSimple.visibility =View.GONE
            holder.binding.lbTextoNota.text = "${objNotaWithCasillas.nota.texto}"
        }else{
            holder.binding.lbTextoNota.visibility = View.GONE
            holder.binding.lstCasillasSimple.visibility =View.VISIBLE
            adapter = ListaCasillasSimpleAdapter(listaCasillas = objNotaWithCasillas.casillas)
            val linearLayoutV= LinearLayoutManager(holder.itemView.context)
            holder.binding.lstCasillasSimple.adapter = adapter
            holder.binding.lstCasillasSimple.layoutManager = linearLayoutV
        }
        holder.binding.layer.setOnClickListener {
            Log.println(Log.INFO,"TOUCH","${position}:${objNotaWithCasillas.nota.titulo} :${objNotaWithCasillas.casillas}")

            when(listNotaWithCasillas[position].nota.tipo){
                "texto"->{
                    val action = MainFragmentDirections.actionAddTaskText(objNotaWithCasillas.nota)
                    holder.itemView.findNavController().navigate(action)

                }
                "casilla"->{
                    val action = MainFragmentDirections.actionAddTask(objNotaWithCasillas)
                    holder.itemView.findNavController().navigate(action)
                }
                else->{}
            }
        }


    }


    override fun getItemCount(): Int {
        return listNotaWithCasillas.size
    }

    fun setData( listNotaWithCasillas: List<NotaWithCasillas>){
        this.listNotaWithCasillas = listNotaWithCasillas
    }
    private fun setupBackgroundColor(holder: NotasViewHolder,color: String){
        var idColor = holder.itemView.resources.getIdentifier("${color}","color", holder.itemView.context.packageName )
        holder.binding.itemNota.setBackgroundResource(idColor)
    }
    }





class NotasViewHolder(val binding: ItemListNotasBinding) :
    RecyclerView.ViewHolder(binding.root){

}
