package com.example.gestiondetareas.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestiondetareas.R
import com.example.gestiondetareas.dal.conn.AppDatabase
import com.example.gestiondetareas.dal.models.Casillas
import com.example.gestiondetareas.dal.models.NotaWithCasillas
import com.example.gestiondetareas.dal.models.Notas
import com.example.gestiondetareas.databinding.FragmentTaskBinding
import com.example.gestiondetareas.ui.adapters.CasillaAdapterListener
import com.example.gestiondetareas.ui.adapters.ListaCasillasAdapter
import com.example.gestiondetareas.ui.adapters.ListaNotasAdapter
import com.google.android.material.snackbar.Snackbar

class TaskFragment : Fragment(), CasillaAdapterListener {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    private var toogle: Boolean = false
    private var color: String = "color_one"
    private lateinit var db: AppDatabase
    private lateinit var adapter: ListaCasillasAdapter

    private var currentNotaWithCasillas: NotaWithCasillas? = null
    private var currentNota: Notas? = null
    private lateinit var listCasillas: ArrayList<Casillas>
    private val args by navArgs<TaskFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        val view = binding.root
        db = AppDatabase.getInstance(requireContext())
        args.let {
            currentNotaWithCasillas = args.currentNotaWithCasillas

        }
        loadData()
        setHasOptionsMenu(true)
        setupClickListener()
        setupColorListener()
        setupRecyclerView()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (currentNotaWithCasillas != null) {
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
        binding.btnPalette2.setOnClickListener {
            if (toogle) {
                binding.palette2.visibility = View.GONE
                toogle = false
            } else {
                binding.palette2.visibility = View.VISIBLE
                toogle = true
            }
        }
        binding.bgTask2.setOnClickListener {
            if (toogle) {
                binding.palette2.visibility = View.GONE
                toogle = false
            }
        }
        if (currentNotaWithCasillas == null) {
            binding.btnGuardarNotaCheckList.setOnClickListener {
                saveData()
                findNavController().popBackStack()
            }
            binding.btnGuardarDatos2.setOnClickListener {
                findNavController().popBackStack()
            }
        } else {
            setVisibilityComponents()
            binding.btnGuardarDatos2.setOnClickListener {
                saveDataUpdate()
            }
            binding.btnGuardarCasAdd.setOnClickListener {
                addCasilla()
            }
        }
    }

    private fun setupRecyclerView() {
        if (currentNotaWithCasillas != null) {
            listCasillas = currentNotaWithCasillas!!.casillas as ArrayList<Casillas>
            adapter = ListaCasillasAdapter(listCasillas, this)
            val linearLayoutV = LinearLayoutManager(requireContext())
            binding.lstCasillas.adapter = adapter
            binding.lstCasillas.layoutManager = linearLayoutV
        }
    }

    private fun setupColorListener() {
        binding.colorOne.setOnClickListener {
            setupBackgroundColor("color_one")
        }
        binding.colorTwo.setOnClickListener {
            setupBackgroundColor("color_two")
        }
        binding.colorThree.setOnClickListener {
            setupBackgroundColor("color_three")
        }
        binding.colorFour.setOnClickListener {
            setupBackgroundColor("color_four")
        }
        binding.colorFive.setOnClickListener {
            setupBackgroundColor("color_five")
        }
        binding.colorSix.setOnClickListener {
            setupBackgroundColor("color_six")
        }
        binding.colorSeven.setOnClickListener {
            setupBackgroundColor("color_seven")
        }
        binding.colorEight.setOnClickListener {
            setupBackgroundColor("color_eight")
        }
        binding.colorNine.setOnClickListener {
            setupBackgroundColor("color_nine")
        }
        binding.colorTen.setOnClickListener {
            setupBackgroundColor("color_ten")
        }
    }

    private fun setupBackgroundColor(color: String) {
        this.color = color
        val idColor = resources.getIdentifier("${color}", "color", requireContext().packageName)
        binding.bgTask2.setBackgroundResource(idColor)
    }

    private fun setVisibilityComponents() {
        binding.txtTextoCasilla.visibility = View.VISIBLE
        binding.btnGuardarCasAdd.visibility = View.VISIBLE
        binding.btnGuardarNotaCheckList.visibility = View.GONE
        binding.btnGuardarDatos2.visibility = View.VISIBLE
    }

    private fun setMessage(texto: String) {
        Snackbar.make(binding.root.rootView, "${texto}", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private fun inputCheckEmpty(
        titulo: String
    ): Boolean {
        return !(TextUtils.isEmpty(titulo))
    }

    private fun loadData() {
        if (currentNotaWithCasillas != null) {
            currentNota = currentNotaWithCasillas!!.nota
            listCasillas = currentNotaWithCasillas!!.casillas as ArrayList<Casillas>
            binding.txtTituloNota2.setText("${currentNotaWithCasillas!!.nota.titulo}")
            setupBackgroundColor(currentNotaWithCasillas!!.nota.color)
            if (!currentNotaWithCasillas!!.casillas.isEmpty()) {
                adapter = ListaCasillasAdapter(listCasillas!!, this)
                val linearLayoutV = LinearLayoutManager(requireContext())
                binding.lstCasillas.adapter = adapter
                binding.lstCasillas.layoutManager = linearLayoutV
            }
        }

    }

    private fun saveData() {
        val titulo = binding.txtTituloNota2.text.toString()
        val tipo = "casilla"
        val color = this.color

        if (inputCheckEmpty(titulo)) {
            addDataToDatabase(titulo, tipo, color)
        } else {
            setMessage("Rellenar los campos")
        }
    }

    private fun saveDataUpdate() {
        val titulo = binding.txtTituloNota2.text.toString()
        val tipo = "casilla"
        val color = this.color

        if (inputCheckEmpty(titulo)) {
            updateDataToDatabase(currentNotaWithCasillas!!.nota.id, titulo, tipo, color)

        } else {
            setMessage("Rellenar los campos")
        }
    }

    private fun addDataToDatabase(
        titulo: String, tipo: String, color: String
    ) {
        val nota = Notas(0, titulo = titulo, texto = "", tipo = tipo, color = color)
        db.notasDao().insert(nota)
        setVisibilityComponents()
    }

    private fun updateDataToDatabase(
        id: Int, titulo: String, tipo: String, color: String
    ) {
        val nota = Notas(id, titulo = titulo, texto = "", tipo = tipo, color = color)
        db.notasDao().update(nota)
        findNavController().popBackStack()
    }

    private fun deleteItem() {
        db.notasDao().delete(currentNotaWithCasillas!!.nota)
        setMessage("Nota eliminada")
        findNavController().popBackStack()
    }

    private fun addCasilla() {
        val texto = binding.txtTextoCasilla.text.toString()
        val estado = false
        val idNota = currentNotaWithCasillas!!.nota.id
        if (inputCheckEmpty(texto)) {
            val casilla = Casillas(0, texto = texto, estado = estado, idNota = idNota)
            db.casillasDao().insert(casilla)
            binding.txtTextoCasilla.text.clear()
            setMessage("Agregado")
            loadDataCasilla(idNota)
            adapter.setData(listCasillas)
        } else {
            setMessage("El campo no puede se nulo")
        }
    }

    private fun loadDataCasilla(idNota: Int) {
        listCasillas = db.casillasDao().getAllByNota(idNota) as ArrayList<Casillas>
    }

    override fun onEditCasilla(casillas: Casillas) {
        db.casillasDao().update(casillas)
        setMessage("Casilla editada")
    }

    override fun onDeleteCasillas(casillas: Casillas) {
        setMessage("Casilla retirada ${casillas.texto}")
//
//        db.casillasDao().delete(casillas)
//        loadDataCasilla(currentNotaWithCasillas!!.nota.id)
//        adapter.setData(listCasillas)
//        binding.lstCasillas.visibility = View.GONE
//        binding.lstCasillas.visibility = View.VISIBLE
        db.casillasDao().delete(casillas)
        adapter.deleteItem(casillas)

    }

}