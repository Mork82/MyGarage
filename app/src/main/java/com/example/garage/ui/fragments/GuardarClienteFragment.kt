package com.example.garage.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.garage.R
import com.example.garage.databinding.FragmentGuardarClienteBinding
import com.example.garage.viewmodels.ClienteViewModel
import com.google.android.material.textfield.TextInputEditText


class GuardarClienteFragment : Fragment() {
    private var _binding: FragmentGuardarClienteBinding? = null
    private val model: ClienteViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGuardarClienteBinding.inflate(inflater, container, false)
        val binding = _binding!!
        val view = binding.root

        binding.guardarClienteBtnGuardar.setOnClickListener {
            //TODO Guardar en Firestore
            val nombre = binding.guardarClienteTieNombre
            val apellido = binding.guardarClienteTieApellido
            val apellido2 = binding.guardarClienteTieApellido2
            val direccion = binding.guardarClienteTieDireccion
            val poblacion = binding.guardarClienteTiePoblacion
            val provincia = binding.guardarClienteTieProvincia
            val cPostal = binding.guardarClienteTieCp
            val telefono = binding.guardarClienteTiePhone


            model.saveCliente(
                nombre.getString(),
                apellido.getString(),
                apellido2.getString(),
                direccion.getString(),
                poblacion.getString(),
                provincia.getString(),
                cPostal.text.toString().toInt(),
                telefono.text.toString().toInt()
            )

            NavHostFragment.findNavController(this)
                .navigate(R.id.action_guardarClienteFragment_to_nav_home)
        }




        return view
    }

    fun TextInputEditText.getString(): String {
        return text.toString()
    }
}