package com.example.gestiondetareas.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gestiondetareas.R
import com.example.gestiondetareas.dal.conn.AppDatabase
import com.example.gestiondetareas.dal.models.Notas
import com.example.gestiondetareas.databinding.FragmentTaskTextBinding
import com.google.android.material.snackbar.Snackbar


class TaskTextFragment : Fragment() {
    private var _binding: FragmentTaskTextBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<TaskTextFragmentArgs>()
    private lateinit var db: AppDatabase
    private var currentNota: Notas? = null
    private var toogle: Boolean = false
    private var color: String = "color_one"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskTextBinding.inflate(inflater, container, false)
        val view = binding.root
        db = AppDatabase.getInstance(requireContext())
        args.let {
            currentNota = it.currentNota
        }
        setHasOptionsMenu(true)
        setupClickListener()
        setupColorListener()
        loadData()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (currentNota != null) {
            inflater.inflate(R.menu.delete_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.btn_delete_nota) {
            deleteItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupClickListener() {
        binding.btnPalette.setOnClickListener {
            if (toogle) {
                binding.palette.visibility = View.GONE
                toogle = false
            } else {
                binding.palette.visibility = View.VISIBLE
                toogle = true
            }
        }
        binding.bgTaskText.setOnClickListener {
            if (toogle) {
                binding.palette.visibility = View.GONE
                toogle = false
            }
        }
        binding.btnGuardarDatos.setOnClickListener {
            saveData()
        }
    }

    private fun setupColorListener() {
        binding.colorOne2.setOnClickListener {
//            binding.bgTaskText.setBackgroundResource(R.color.color_one)
//            this.color = "color_one"
            setupBackgroundColor("color_one")
        }
        binding.colorTwo2.setOnClickListener {
            setupBackgroundColor("color_two")
        }
        binding.colorThree2.setOnClickListener {
            setupBackgroundColor("color_three")
        }
        binding.colorFour2.setOnClickListener {
            setupBackgroundColor("color_four")
        }
        binding.colorFive2.setOnClickListener {
            setupBackgroundColor("color_five")
        }
        binding.colorSix2.setOnClickListener {
            setupBackgroundColor("color_six")
        }
        binding.colorSeven2.setOnClickListener {
            setupBackgroundColor("color_seven")
        }
        binding.colorEight2.setOnClickListener {
            setupBackgroundColor("color_eight")
        }
        binding.colorNine2.setOnClickListener {
            setupBackgroundColor("color_nine")
        }
        binding.colorTen2.setOnClickListener {
            setupBackgroundColor("color_ten")
        }
    }



    private fun saveData() {
        val titulo = binding.txtTituloNota.text.toString()
        val texto = binding.txtTextoNota.text.toString()
        val tipo = "texto"
        val color = this.color
        if (inputCheckEmpty(titulo, texto)) {
            if (currentNota == null) {
                addDataToDatabase(titulo, texto, tipo, color)
            } else {
                updateDataToDatabase(currentNota!!.id, titulo, texto, tipo, color)
            }
        } else {
           msg("Rellenar los campos")
        }
    }

    private fun addDataToDatabase(
        titulo: String, texto: String, tipo: String, color: String
    ) {
        val nota = Notas(0, titulo = titulo, texto = texto, tipo = tipo, color = color)
        db.notasDao().insert(nota)
        msg("Nota guardada")
        findNavController().popBackStack()
    }

    private fun updateDataToDatabase(
        id: Int, titulo: String, texto: String, tipo: String, color: String
    ) {
        val nota = Notas(id, titulo = titulo, texto = texto, tipo = tipo, color = color)
        db.notasDao().update(nota)
        msg("Nota actualizada")
        findNavController().popBackStack()
    }
    private fun deleteItem() {
        db.notasDao().delete(currentNota!!)
        msg("Nota eliminada")
        findNavController().popBackStack()
    }

    private fun inputCheckEmpty(
        titulo: String,
        texto: String
    ): Boolean {
        return !(TextUtils.isEmpty(titulo) && TextUtils.isEmpty(texto))
    }

    private fun loadData() {
        if (currentNota != null) {
            binding.txtTituloNota.setText("${currentNota!!.titulo}")
            binding.txtTextoNota.setText("${currentNota!!.texto}")
            setupBackgroundColor(color = currentNota!!.color)

        }
    }

    private fun setupBackgroundColor(color: String) {
        this.color = color
        val idColor = resources.getIdentifier("${color}", "color", requireContext().packageName)
        binding.bgTaskText.setBackgroundResource(idColor)
    }

    private fun msg(texto: String) {
        Snackbar.make(binding.root.rootView, "${texto}", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

}

