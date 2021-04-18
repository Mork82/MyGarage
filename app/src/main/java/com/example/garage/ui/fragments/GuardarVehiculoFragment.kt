package com.example.garage.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.garage.R
import com.example.garage.databinding.FragmentGuardarVehiculoBinding
import com.example.garage.viewmodels.VehiculoViewModel
import com.google.android.material.textfield.TextInputEditText

class GuardarVehiculoFragment : Fragment() {
    private var _binding: FragmentGuardarVehiculoBinding? = null
    private val model: VehiculoViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGuardarVehiculoBinding.inflate(inflater, container, false)
        val binding = _binding!!
        val view = binding.root

        binding.insertarVehiculoBtnGuardar.setOnClickListener {

            val matricula = binding.insertarVeviculoTieMatricula
            val vin = binding.insertarVeviculoTieBastidor
            val marca = binding.insertarVeviculoTieMarca
            val modelo = binding.insertarVeviculoTieModelo
            val color = binding.insertarVeviculoTieColor
            val neumaticos = binding.insertarVeviculoTieNeumaticos
            val kilometros = binding.insertarVeviculoTieKm

            model.saveVehiculo(
                matricula.getString(),
                vin.getString(),
                marca.getString(),
                modelo.getString(),
                color.getString(),
                neumaticos.getString(),
                kilometros.text.toString().toLong()
            )



            NavHostFragment.findNavController(this)
                .navigate(R.id.action_guardarVehiculoFragment_to_vehiculoFragment)
        }

        return view
    }

    private fun TextInputEditText.getString(): String {
        return text.toString()
    }

}