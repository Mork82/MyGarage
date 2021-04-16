package com.example.garage.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.garage.databinding.ItemClientesRvBinding
import com.example.garage.entities.Cliente

class ClienteAdapter() :
    RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {
    val lista = mutableListOf<Cliente>()
    private lateinit var listaOriginal: List<Cliente>

    fun clienteAdapter(lista: List<Cliente>) {
        this.lista.addAll(lista)
        listaOriginal = lista
    }

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
        holder.rellenarDatosCliente(lista[position])


    override fun getItemCount() = lista.size

    fun buscar(apellido: String) {
        val buscador = mutableListOf<Cliente>()
        for (cliente in listaOriginal) {
            if (cliente.apellido.toLowerCase().contains(apellido.toLowerCase())) {
                buscador.add(cliente)
            }

            lista.clear()
            lista.addAll(buscador)
            notifyDataSetChanged()
        }
    }

}