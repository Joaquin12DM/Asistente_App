package com.cibertec.asistentememoria.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.model.RecordatorioResponse

class RecordatorioAdapter(private var recordatorios: List<RecordatorioResponse>, private val onEliminarClick: (RecordatorioResponse) -> Unit
) : RecyclerView.Adapter<RecordatorioAdapter.RecordatorioViewHolder>() {

    class RecordatorioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitulo: TextView = view.findViewById(R.id.tvTitulo)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val tvFechaHora: TextView = view.findViewById(R.id.tvFechaHora)
        val tvFrecuencia: TextView = view.findViewById(R.id.tvFrecuencia)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordatorioAdapter.RecordatorioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recordatorio, parent, false)
        return RecordatorioViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecordatorioAdapter.RecordatorioViewHolder, position: Int) {
        val recordatorio = recordatorios[position]
        holder.tvTitulo.text = recordatorio.titulo
        holder.tvDescripcion.text = recordatorio.descripcion
        holder.tvFechaHora.text = recordatorio.fechaHora
        holder.tvFrecuencia.text = formatearFrecuencia(recordatorio.frecuencia)

        holder.btnEliminar.setOnClickListener {
            onEliminarClick(recordatorio)
        }
    }

    override fun getItemCount() = recordatorios.size

    fun actualizarLista(nuevosRecordatorios: List<RecordatorioResponse>) {
        recordatorios = nuevosRecordatorios
        notifyDataSetChanged()
    }

    private fun formatearFrecuencia(frecuencia: String): String {
        return when (frecuencia) {
            "UNA_VEZ" -> "Una vez"
            "DIARIO" -> "Diario"
            "CADA_8_HORAS" -> "Cada 8 horas"
            "CADA_12_HORAS" -> "Cada 12 horas"
            "SEMANAL" -> "Semanal"
            else -> frecuencia
        }
    }
}

