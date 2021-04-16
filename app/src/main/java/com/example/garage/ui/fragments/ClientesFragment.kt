package com.example.garage.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.garage.R
import com.example.garage.adapters.ClienteAdapter
import com.example.garage.databinding.FragmentClientesBinding
import com.example.garage.entities.Cliente
import com.example.garage.viewmodels.ClienteViewModel

class ClientesFragment : Fragment() {

    private val model: ClienteViewModel by viewModels()
    private var _binding: FragmentClientesBinding? = null
    var mAdapter = ClienteAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentClientesBinding.inflate(inflater, container, false)
        val binding = _binding!!
        val view = binding.root
        setHasOptionsMenu(true)




        model.getClientes().observe(viewLifecycleOwner, {
            createRecyclerView(it)
        })

        binding.clientesFab.setOnClickListener {

            NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_home_to_guardarClienteFragment)
        }
        return view
    }

    fun createRecyclerView(clientes: List<Cliente>) {
        mAdapter.clienteAdapter(clientes)
        val rv = _binding!!.recylerViewClientes
        rv.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }

    }

    //FUNCION PARA INFLAR LA LUPA EN EL MENU
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
    }

    //FUNCION PARA DARLE FUNCIONALIDAD AL BOTON DE BUSCAR

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                val searchView = item.actionView as SearchView
                searchView.queryHint= getString(R.string.query_dni)
               searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                   override fun onQueryTextSubmit(query: String?): Boolean {
                       return false
                   }

                   override fun onQueryTextChange(newText: String?): Boolean {
                       mAdapter.buscar(newText!!)
                       return true
                   }
               })



            }
        }
        return false
    }
}