package com.cibertec.asistentememoria.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.model.MomentoResponse

class RecuerdosAdapter(
    private var momentos: List<MomentoResponse>
) : RecyclerView.Adapter<RecuerdosAdapter.RecuerdoViewHolder>() {

    class RecuerdoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitulo: TextView = view.findViewById(R.id.tituloRecuerdo)
        val tvFecha: TextView = view.findViewById(R.id.fechaRecuerdo)
        val tvDescripcion: TextView = view.findViewById(R.id.descripcionRecuerdo)
        val layoutPersonas: LinearLayout = view.findViewById(R.id.layoutPersonas)
        val tvPersonas: TextView = view.findViewById(R.id.personasRecuerdo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecuerdoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recuerdo, parent, false)
        return RecuerdoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecuerdoViewHolder, position: Int) {
        val momento = momentos[position]

        holder.itemView.findViewById<TextView>(R.id.IdRecuerdo).text = momento.id.toString()
        holder.tvTitulo.text = momento.titulo
        holder.tvFecha.text = momento.fecha
        holder.tvDescripcion.text = momento.descripcion

        if (!momento.personasPresentes.isNullOrBlank()) {
            holder.layoutPersonas.visibility = View.VISIBLE
            holder.tvPersonas.text = momento.personasPresentes
        } else {
            holder.layoutPersonas.visibility = View.GONE
        }
    }

    override fun getItemCount() = momentos.size

    fun actualizarLista(nuevosMomentos: List<MomentoResponse>) {
        momentos = nuevosMomentos
        notifyDataSetChanged()
    }
}
