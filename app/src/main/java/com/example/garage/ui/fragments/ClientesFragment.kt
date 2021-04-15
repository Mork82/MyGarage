package com.example.garage.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.garage.R
import com.example.garage.adapters.ClienteAdapter
import com.example.garage.databinding.FragmentClientesBinding
import com.example.garage.entityes.Cliente
import com.example.garage.viewmodels.ClienteViewModel

class ClientesFragment : Fragment() {

    private val model: ClienteViewModel by viewModels()
    private var _binding: FragmentClientesBinding? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentClientesBinding.inflate(inflater, container, false)
        val binding = _binding!!
        val view = binding.root




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
       val mAdapter=ClienteAdapter(clientes as MutableList<Cliente>)
        val rv = _binding!!.recylerViewClientes
        rv.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }

    }

}