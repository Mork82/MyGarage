package com.example.garage.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.garage.R
import com.example.garage.databinding.FragmentGuardarRecambioBinding
import com.example.garage.viewmodels.RecambioViewModel
import com.google.android.material.textfield.TextInputEditText

class GuardarRecambioFragment : Fragment() {

    private var _binding: FragmentGuardarRecambioBinding? = null
    private val model: RecambioViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGuardarRecambioBinding.inflate(inflater, container, false)
        val binding = _binding!!
        val view = binding.root

        binding.guardarRecambioBtnGuardar.setOnClickListener {

            val referencia = binding.guardarRecambioTieReferencia
            val articulo= binding.guardarRecambioTieArticulo
            val descripcion = binding.guardarRecambioTieDescripcion

            model.saveRecambio(
                referencia.getString(),
                articulo.getString(),
                descripcion.getString()
            )

            NavHostFragment.findNavController(this)
                .navigate(R.id.action_guardarRecambioFragment_to_recambioFragment2)
        }


        return view
    }

    private fun TextInputEditText.getString(): String {
        return text.toString()
    }
}