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
import com.example.garage.adapters.VehiculoAdapter
import com.example.garage.databinding.FragmentVehiculoBinding
import com.example.garage.entities.Vehiculo
import com.example.garage.ui.activitys.DatosVehiculoActivity
import com.example.garage.viewmodels.VehiculoViewModel


class VehiculoFragment : Fragment(),VehiculoAdapter.OnVehiculoClickListener {
    private var _binding: FragmentVehiculoBinding? = null
    private val model :VehiculoViewModel by viewModels()
    var mAdapter = VehiculoAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding= FragmentVehiculoBinding.inflate(inflater,container,false)
        val binding = _binding!!
        val view = binding.root

        setHasOptionsMenu(true)

        model.getVehiculos().observe(viewLifecycleOwner,{
            createRecyclerView(it)
        })


        binding.vehiculosFab.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_vehiculoFragment_to_guardarVehiculoFragment)
        }


        return view
    }

    private fun createRecyclerView(vehiculos:List<Vehiculo>) {
        mAdapter.vehiculoAdapter(vehiculos)
        val rv = _binding!!.recyclerViewVehiculos
        rv.apply {
            layoutManager=LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            adapter=mAdapter
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
                searchView.queryHint= getString(R.string.query_matricula)
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

    override fun onItemClick(item: Vehiculo, position: Int) {
        val intent= Intent(requireContext(), DatosVehiculoActivity::class.java)
        intent.putExtra("Matricula", item.matricula)
        intent.putExtra("bastidor", item.bastidor)
        intent.putExtra("Marca",item.marca)
        intent.putExtra("Modelo", item.modelo)
        intent.putExtra("Color", item.color)
        intent.putExtra("Neumaticos", item.neumaticos)
        intent.putExtra("Km", item.kilometros)
        startActivity(intent)
    }


}