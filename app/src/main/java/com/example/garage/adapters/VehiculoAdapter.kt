package com.example.garage.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.garage.databinding.ItemVehiculosRecyclerViewBinding
import com.example.garage.entities.Cliente
import com.example.garage.entities.Vehiculo

class VehiculoAdapter(var clicklListener: OnVehiculoClickListener) :
    RecyclerView.Adapter<VehiculoAdapter.VehiculoViewHolder>() {
    val lista = mutableListOf<Vehiculo>()
    private lateinit var listaOriginal: List<Vehiculo>

    fun vehiculoAdapter(lista: List<Vehiculo>) {
        this.lista.addAll(lista)
        listaOriginal = lista
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculoViewHolder {
        return VehiculoViewHolder.crearViewHolder(parent)
    }

    override fun onBindViewHolder(holder: VehiculoViewHolder, position: Int) =
        holder.rellenarDatosVehiculo(lista[position], clicklListener)

    override fun getItemCount() = lista.size


    fun buscar(matricula: String) {
        val buscador = mutableListOf<Vehiculo>()
        for (vehiculo in listaOriginal) {
            if (vehiculo.matricula.toLowerCase().contains(matricula.toLowerCase())) {
                buscador.add(vehiculo)
            }
            lista.clear()
            lista.addAll(buscador)
            notifyDataSetChanged()
        }

    }


    class VehiculoViewHolder(val binding: ItemVehiculosRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun rellenarDatosVehiculo(vehiculo: Vehiculo, action: OnVehiculoClickListener) {
            binding.itemVehiculoTvMatricula.text = vehiculo.matricula
            binding.itemVehiculoTvMarca.text = vehiculo.marca
            binding.itemVehiculoTvModelo.text = vehiculo.modelo

            binding.root.setOnClickListener {
                action.onItemClick(vehiculo,adapterPosition )

            }
        }

        companion object {
            fun crearViewHolder(parent: ViewGroup): VehiculoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemVehiculosRecyclerViewBinding.inflate(layoutInflater, parent, false)
                return VehiculoViewHolder(binding)
            }
        }

    }
    interface OnVehiculoClickListener {
        fun onItemClick(item: Vehiculo, position: Int)

    }
}


