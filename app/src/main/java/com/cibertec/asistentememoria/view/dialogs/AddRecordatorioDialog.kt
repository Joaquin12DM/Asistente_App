package com.cibertec.asistentememoria.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.controller.RecordatorioController
import com.cibertec.asistentememoria.model.RecordatorioRequest
import com.cibertec.asistentememoria.model.RecordatorioResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddRecordatorioDialog: DialogFragment() {

    var onRecordatorioCreado: (() -> Unit)? = null

    var usuarioId: Int = 0

    private val controllerRecordatorio = RecordatorioController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usuarioId = arguments?.getInt(USER_ID, 0) ?: 0
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_nuevo_reminder, container, false)

        val tipoSpinner = view.findViewById<Spinner>(R.id.spinnerTipo)
        val nombreEditText = view.findViewById<EditText>(R.id.editTextNombre)
        val descripcionEditText = view.findViewById<EditText>(R.id.editTextDescripcion)
        val fechaEditText = view.findViewById<EditText>(R.id.editTextFecha)
        val horaEditText = view.findViewById<EditText>(R.id.editTextHora)
        val frecuenciaSpinner = view.findViewById<Spinner>(R.id.spinnerFrecuencia)
        val agregarButton = view.findViewById<Button>(R.id.buttonAgregarRecuerdo)

        tipoSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Medicamento", "Cita Médica")
        )

        frecuenciaSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Una vez", "Diario", "Cada 8 horas", "Cada 12 horas", "Semanal")
        )

        agregarButton.setOnClickListener {
            val tipo = tipoSpinner.selectedItem as String
            val titulo = nombreEditText.text.toString().trim()
            val descripcion = descripcionEditText.text.toString().trim()
            val fecha = fechaEditText.text.toString().trim()
            val hora = horaEditText.text.toString().trim()
            val frecuenciaTexto = frecuenciaSpinner.selectedItem as String

            val fechaHora = "$fecha $hora"
            val frecuenciaCodigo = when (frecuenciaTexto) {
                "Una vez" -> "UNA_VEZ"
                "Diario" -> "DIARIO"
                "Cada 8 horas" -> "CADA_8_HORAS"
                "Cada 12 horas" -> "CADA_12_HORAS"
                "Semanal" -> "SEMANAL"
                else -> frecuenciaTexto
            }

            if (titulo.isEmpty()) {
                Toast.makeText(requireContext(), "Ingrese un título", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            crearRecordatorio(usuarioId, tipo, titulo, descripcion, fechaHora, frecuenciaCodigo)
        }

        return view
    }

    private fun crearRecordatorio(usuario: Int, tipo: String, titulo: String, descripcion: String, fechaHora: String, frecuencia: String) {
        val recordatorio = RecordatorioRequest(
            usuario = usuario,
            tipo = tipo,
            titulo = titulo,
            descripcion = descripcion,
            fechaHora = fechaHora,
            frecuencia = frecuencia
        )

        controllerRecordatorio.createRecordatorio(recordatorio).enqueue(object : Callback<RecordatorioResponse> {
                override fun onResponse(call: Call<RecordatorioResponse>, response: Response<RecordatorioResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Recordatorio creado", Toast.LENGTH_SHORT).show()
                        onRecordatorioCreado?.invoke()
                        dismiss()
                    } else {
                        Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RecordatorioResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Fallo en la conexión: ${t.localizedMessage ?: "error"}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    companion object {
        private const val USER_ID = "arg_user_id"
        fun newInstance(userId: Int): AddRecordatorioDialog {
            return AddRecordatorioDialog().apply {
                arguments = Bundle().apply {
                    putInt(USER_ID, userId)
                }
            }
        }
    }



}