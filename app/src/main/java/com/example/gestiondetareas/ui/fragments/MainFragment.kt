package com.example.gestiondetareas.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestiondetareas.R
import com.example.gestiondetareas.dal.conn.AppDatabase
import com.example.gestiondetareas.dal.models.NotaWithCasillas
import com.example.gestiondetareas.databinding.FragmentMainBinding
import com.example.gestiondetareas.ui.adapters.ListaNotasAdapter

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ListaNotasAdapter
    private lateinit var listNotaWithCasillas: ArrayList<NotaWithCasillas>
    private lateinit var db:AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        db = AppDatabase.getInstance(requireContext())
        listNotaWithCasillas= db.notasDao().getNotasWithCasillas() as ArrayList<NotaWithCasillas>
        loadBaseData()

        setHasOptionsMenu(true)
        setupRecyclerView()
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        listNotaWithCasillas = db.notasDao().getNotasWithCasillas() as ArrayList<NotaWithCasillas>
        adapter.setData(listNotaWithCasillas)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btn_add_nota_checklist ->{
                setupActionAddNotaChecklist()
            }
            R.id.btn_add_nota_text->{
                setupActionAddNotaText()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setupActionAddNotaChecklist(){
       val action = MainFragmentDirections.actionAddTask(null)
        findNavController().navigate(action)
    }
    private fun setupActionAddNotaText(){
        val action = MainFragmentDirections.actionAddTaskText(null)
        findNavController().navigate(action)
    }
    private fun setupRecyclerView(){
        adapter = ListaNotasAdapter(listNotaWithCasillas)
        val linearLayoutV= LinearLayoutManager(requireContext())
        binding.lstNotas.adapter = adapter
        binding.lstNotas.layoutManager = linearLayoutV
    }

    private fun loadBaseData(){
        listNotaWithCasillas = db.notasDao().getNotasWithCasillas() as ArrayList<NotaWithCasillas>
    }


}