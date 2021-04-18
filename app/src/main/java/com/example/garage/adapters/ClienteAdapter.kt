package com.example.garage.adapters


import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.garage.App
import com.example.garage.databinding.ItemClientesRvBinding
import com.example.garage.entities.Cliente
import com.google.android.material.snackbar.Snackbar

class ClienteAdapter(var clickListener: OnClienteClickListener):
    RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {
    val lista = mutableListOf<Cliente>()
    private lateinit var listaOriginal: List<Cliente>

    fun clienteAdapter(lista: List<Cliente>) {
        this.lista.addAll(lista)
        listaOriginal = lista
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        return ClienteViewHolder.crearViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) =
        holder.rellenarDatosCliente(lista[position], clickListener)


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

    class ClienteViewHolder(val binding: ItemClientesRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun rellenarDatosCliente(cliente: Cliente, action:OnClienteClickListener) {
            binding.itemClienteTvNombre.text = cliente.nombre
            binding.itemClienteTvApellido.text = cliente.apellido
            binding.itemClienteTvDni.text = cliente.dni


            binding.root.setOnClickListener {
                action.onItemClick(cliente,adapterPosition )

            }
        }

        companion object {
            fun crearViewHolder(parent: ViewGroup): ClienteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemClientesRvBinding.inflate(layoutInflater, parent, false)
                return ClienteViewHolder(binding)
            }
        }
    }
    interface OnClienteClickListener {
        fun onItemClick(item: Cliente, position: Int)
    }

}