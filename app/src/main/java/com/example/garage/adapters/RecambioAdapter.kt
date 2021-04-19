package com.example.garage.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.garage.databinding.ItemRecambioRecyclerViewBinding
import com.example.garage.entities.Cliente
import com.example.garage.entities.Recambio

class RecambioAdapter(val clickListener: OnRecambioClickListener) :
    RecyclerView.Adapter<RecambioAdapter.RecambioViewHolder>() {
    val lista = mutableListOf<Recambio>()
    private lateinit var listaOriginal: List<Recambio>

    fun recambioAdapter(lista: List<Recambio>) {
        this.lista.addAll(lista)
        listaOriginal = lista
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecambioViewHolder {
        return RecambioViewHolder.crearViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecambioViewHolder, position: Int) =
        holder.rellenarDatosRecambio(lista[position], clickListener)

    override fun getItemCount() = lista.size

    fun buscar(referencia: String) {
        val buscador = mutableListOf<Recambio>()
        for (recambio in listaOriginal) {
            if (recambio.referencia.toLowerCase().contains(referencia.toLowerCase())) {
                buscador.add(recambio)
            }
            lista.clear()
            lista.addAll(buscador)
            notifyDataSetChanged()
        }

    }


    class RecambioViewHolder(var binding: ItemRecambioRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun rellenarDatosRecambio(recambio: Recambio, action: OnRecambioClickListener) {
            binding.itemRecambioTvReferencia.text = recambio.referencia
            binding.itemRecambioTvArticulo.text = recambio.articulo
            binding.itemRecambioTvDescripcion.text = recambio.descipcion

            binding.root.setOnClickListener {
                action.onItemClick(recambio, adapterPosition)
            }


        }

        companion object {
            fun crearViewHolder(parent: ViewGroup): RecambioViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRecambioRecyclerViewBinding.inflate(layoutInflater, parent, false)

                return RecambioViewHolder(binding)
            }
        }

    }

    interface OnRecambioClickListener {
        fun onItemClick(item: Recambio, position: Int)
    }


}


