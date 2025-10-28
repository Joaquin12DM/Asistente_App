package com.cibertec.asistentememoria.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.controller.RecordatorioController
import com.cibertec.asistentememoria.model.RecordatorioResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var userId: Int = 0
    private lateinit var tvBienvenida: TextView
    private lateinit var tvFecha: TextView
    private lateinit var tvFraseDelDia: TextView
    private lateinit var tvConsejo: TextView
    private lateinit var tvSinRecordatorios: TextView
    private lateinit var recyclerViewRecordatoriosHoy: RecyclerView
    private val recordatorioController = RecordatorioController()

    private val frases = listOf(
        "Cada día es una nueva oportunidad para ser feliz",
        "La memoria es el diario que todos llevamos con nosotros",
        "Los recuerdos son tesoros que guardamos en el corazón",
        "Hoy es un buen día para crear nuevos recuerdos",
        "La vida está llena de pequeños momentos mágicos",
        "Cada momento cuenta, disfrútalo al máximo",
        "Los mejores recuerdos están por crearse"
    )

    private val consejos = listOf(
        "Mantén una rutina diaria constante. Esto ayuda a tu cerebro a recordar mejor las actividades importantes.",
        "Escribe notas y usa recordatorios. No confíes solo en tu memoria para cosas importantes.",
        "Duerme lo suficiente. El sueño ayuda a consolidar los recuerdos.",
        "Mantente activo físicamente. El ejercicio mejora la circulación cerebral y la memoria.",
        "Socializa regularmente. Interactuar con otros estimula tu mente y mejora tu bienestar.",
        "Come saludable. Una dieta equilibrada rica en omega-3 beneficia tu cerebro.",
        "Ejercita tu mente. Lee, resuelve acertijos o aprende algo nuevo cada día.",
        "Organiza tu espacio. Mantén las cosas importantes siempre en el mismo lugar.",
        "Usa asociaciones. Conecta nueva información con cosas que ya conoces.",
        "Reduce el estrés. La relajación y la meditación ayudan a mejorar la memoria."
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val sharedPreferences = requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        userId = sharedPreferences.getInt("userId", -1)

        tvBienvenida = view.findViewById(R.id.tvBienvenida)
        tvFecha = view.findViewById(R.id.tvFecha)
        tvFraseDelDia = view.findViewById(R.id.tvFraseDelDia)
        tvConsejo = view.findViewById(R.id.tvConsejo)

        configurarBienvenida()
        configurarFraseDelDia()
        configurarConsejo()

        return view
    }

    private fun configurarBienvenida() {
        val nombreUsuario = requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            .getString("userName", "Usuario") ?: "Usuario"
        tvBienvenida.text = "¡Hola, $nombreUsuario!"

        val fechaActual = SimpleDateFormat("EEEE, d 'de' MMMM yyyy", Locale("es", "ES"))
            .format(Date())
        tvFecha.text = fechaActual.replaceFirstChar { it.uppercase() }
    }

    private fun configurarFraseDelDia() {
        tvFraseDelDia.text = frases.random()
    }

    private fun configurarConsejo() {
        tvConsejo.text = consejos.random()
    }



}
