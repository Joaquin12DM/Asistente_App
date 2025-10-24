package com.cibertec.asistentememoria.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.controller.MomentoController
import com.cibertec.asistentememoria.model.MomentoRequest
import com.cibertec.asistentememoria.model.MomentoResponse
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddRecuerdoDialog : DialogFragment() {

    var onRecuerdoCreado: (() -> Unit)? = null
    var usuarioId: Int = 0

    private val controllerMomento = MomentoController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usuarioId = arguments?.getInt(USER_ID, 0) ?: 0


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_nuevo_recuerdo, container, false)

        val editTitulo = view.findViewById<TextInputEditText>(R.id.editTitulo)
        val editDescripcion = view.findViewById<TextInputEditText>(R.id.editDescripcion)
        val editFecha = view.findViewById<TextInputEditText>(R.id.editFecha)
        val editHora = view.findViewById<TextInputEditText>(R.id.editHora)
        val editPersonas = view.findViewById<TextInputEditText>(R.id.editPersonas)
        val buttonGuardar = view.findViewById<Button>(R.id.buttonGuardar)
        val buttonCancelar = view.findViewById<Button>(R.id.buttonCancelar)

        buttonGuardar.setOnClickListener {
            val titulo = editTitulo.text.toString().trim()
            val descripcion = editDescripcion.text.toString().trim()
            val fecha = editFecha.text.toString().trim()
            val hora = editHora.text.toString().trim()
            val personas = editPersonas.text.toString().trim()

            if (titulo.isEmpty()) {
                Toast.makeText(requireContext(), "Ingrese un título", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (descripcion.isEmpty()) {
                Toast.makeText(requireContext(), "Ingrese una descripción", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (fecha.isEmpty() || hora.isEmpty()) {
                Toast.makeText(requireContext(), "Ingrese fecha y hora", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fechaHora = "$fecha $hora"
            crearRecuerdo(usuarioId, titulo, descripcion, fechaHora, personas)
        }

        buttonCancelar.setOnClickListener {
            dismiss()
        }

        return view
    }

    private fun crearRecuerdo(
        usuarioId: Int,
        titulo: String,
        descripcion: String,
        fecha: String,
        personasPresentes: String
    ) {
        val momento = MomentoRequest(
            usuarioId = usuarioId,
            titulo = titulo,
            descripcion = descripcion,
            fecha = fecha,
            personasPresentes = personasPresentes
        )

        controllerMomento.createMomento(momento).enqueue(object : Callback<MomentoResponse> {
            override fun onResponse(call: Call<MomentoResponse>, response: Response<MomentoResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Recuerdo creado", Toast.LENGTH_SHORT).show()
                    onRecuerdoCreado?.invoke()
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MomentoResponse>, exception: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Fallo en la conexión: ${exception.localizedMessage ?: "error"}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    companion object {
        private const val USER_ID = "arg_user_id"
        fun newInstance(userId: Int): AddRecuerdoDialog {
            return AddRecuerdoDialog().apply {
                arguments = Bundle().apply {
                    putInt(USER_ID, userId)
                }
            }
        }
    }
}
