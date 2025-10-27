package com.cibertec.asistentememoria.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.model.FamiliaresResponse

class FamiliarAdapter(private var familiares: List<FamiliaresResponse>) :
    RecyclerView.Adapter<FamiliarAdapter.FamiliarViewHolder>() {

    class FamiliarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreFamiliar: TextView = itemView.findViewById(R.id.nombreFamiliar)
        val relacionFamiliar: TextView = itemView.findViewById(R.id.relacionFamiliar)
        val telefonoFamiliar: TextView = itemView.findViewById(R.id.telefonoFamiliar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamiliarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_familiar, parent, false)
        return FamiliarViewHolder(view)
    }

    override fun onBindViewHolder(holder: FamiliarViewHolder, position: Int) {
        val familiar = familiares[position]
        holder.nombreFamiliar.text = familiar.nombre
        holder.relacionFamiliar.text = familiar.relacion
        holder.telefonoFamiliar.text = familiar.telefono
    }

    override fun getItemCount(): Int = familiares.size

    fun updateFamiliares(nuevosFamiliares: List<FamiliaresResponse>) {
        familiares = nuevosFamiliares
        notifyDataSetChanged()
    }
}
