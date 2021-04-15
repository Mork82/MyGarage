package com.example.garage.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.garage.databinding.ItemClientesRvBinding
import com.example.garage.entityes.Cliente

class ClienteAdapter(val clientes :List<Cliente>) : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {
   // val clientes = mutableListOf<Cliente>()

    class ClienteViewHolder(val binding: ItemClientesRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun rellenarDatosCliente(cliente: Cliente) {
            binding.itemClienteTvNombre.text = cliente.nombre
            binding.itemClienteTvApellido.text = cliente.apellido
            binding.itemClienteTvDni.text = cliente.dni


        }

        companion object {
            fun crearViewHolder(parent: ViewGroup): ClienteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemClientesRvBinding.inflate(layoutInflater, parent, false)
                return ClienteViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        return ClienteViewHolder.crearViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) =
        holder.rellenarDatosCliente(clientes[position])


    override fun getItemCount() = clientes.size

}