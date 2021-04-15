package com.example.garage.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.garage.R
import com.example.garage.databinding.FragmentClientesBinding
import com.example.garage.viewmodels.ClienteViewModel

class ClientesFragment : Fragment() {

    private lateinit var model: ClienteViewModel
    private var _binding: FragmentClientesBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentClientesBinding.inflate(inflater,container,false)
        val binding=_binding!!
        val view = binding.root

        binding.clientesFab.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_nav_home_to_guardarClienteFragment)
        }






        return view
    }
}