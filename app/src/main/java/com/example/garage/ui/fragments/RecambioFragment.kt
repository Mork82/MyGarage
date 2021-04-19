package com.example.garage.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.garage.R

import com.example.garage.adapters.RecambioAdapter
import com.example.garage.databinding.FragmentRecambioBinding
import com.example.garage.entities.Recambio
import com.example.garage.ui.activitys.DatosRecambioActivity
import com.example.garage.viewmodels.RecambioViewModel


class RecambioFragment : Fragment(), RecambioAdapter.OnRecambioClickListener {
    private var _binding : FragmentRecambioBinding?=null
    private val model: RecambioViewModel by viewModels()
    var mAdapter= RecambioAdapter(this)



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding= FragmentRecambioBinding.inflate(inflater,container,false)
        val binding=_binding!!
        val view = binding.root

        setHasOptionsMenu(true)

        model.getRecambios().observe(viewLifecycleOwner,{
            createRecyclerView(it)
        })

        binding.recambioFab.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_recambioFragment2_to_guardarRecambioFragment)
        }

        return view
    }

    private fun createRecyclerView(recambios:List<Recambio>) {
       mAdapter.recambioAdapter(recambios)
        val rv= _binding!!.recyclerViewRecambio
        rv.apply {
         layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL, false)
            adapter=mAdapter
        }
    }

    //FUNCION PARA INFLAR LA LUPA EN EL MENU
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                val searchView = item.actionView as SearchView
                searchView.queryHint= getString(R.string.query_ref)
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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

    override fun onItemClick(item: Recambio, position: Int) {
        val intent = Intent(requireContext(),DatosRecambioActivity::class.java)
        intent.putExtra("Referencia", item.referencia)
        intent.putExtra("Articulo", item.articulo)
        intent.putExtra("Descripcion", item.descipcion)
        startActivity(intent)
    }
}