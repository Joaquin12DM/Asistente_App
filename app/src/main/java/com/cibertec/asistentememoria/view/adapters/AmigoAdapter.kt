package com.cibertec.asistentememoria.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.model.AmigosResponse

class AmigoAdapter(
    private var amigos: List<AmigosResponse>,
    private val onEliminarClick: (AmigosResponse) -> Unit
) : RecyclerView.Adapter<AmigoAdapter.AmigoViewHolder>() {

    class AmigoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreAmigo: TextView = itemView.findViewById(R.id.nombreAmigo)
        val telefonoAmigo: TextView = itemView.findViewById(R.id.telefonoAmigo)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminarAmigo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmigoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_amigos, parent, false)
        return AmigoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AmigoViewHolder, position: Int) {
        val amigo = amigos[position]
        holder.nombreAmigo.text = amigo.nombre
        holder.telefonoAmigo.text = amigo.telefono

        holder.btnEliminar.setOnClickListener {
            onEliminarClick(amigo)
        }
    }

    override fun getItemCount(): Int = amigos.size

    fun updateAmigos(nuevosAmigos: List<AmigosResponse>) {
        amigos = nuevosAmigos
        notifyDataSetChanged()
    }
}