package com.example.garage.ui.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.garage.R
import com.example.garage.databinding.ActivityDatosClienteBinding

class DatosClienteActivity : AppCompatActivity() {

   var _binding:ActivityDatosClienteBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityDatosClienteBinding.inflate(layoutInflater)
         val binding=_binding!!
        val view = binding.root
        setContentView(view)

        binding.textView2.text=getIntent().getStringExtra("Apellido2")
    }
}