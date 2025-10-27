package com.cibertec.asistentememoria.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.controller.SItuacionMedicaController
import com.cibertec.asistentememoria.model.SituacionMedicaRequest
import com.cibertec.asistentememoria.model.SituacionMedicaResponse
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSituacionMedicaDialog : DialogFragment() {

    private var userId: Int = 0
    private var onSituacionMedicaAdded: (() -> Unit)? = null

    private lateinit var editCondicionMedica: TextInputEditText
    private lateinit var editAlergias: TextInputEditText
    private val situMedicaController = SItuacionMedicaController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments?.getInt("userId") ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_nuevo_sitmedica, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editCondicionMedica = view.findViewById(R.id.editCondicionMedica)
        editAlergias = view.findViewById(R.id.editAlergias)

        val buttonGuardar = view.findViewById<Button>(R.id.buttonGuardar)
        val buttonCancelar = view.findViewById<Button>(R.id.buttonCancelar)

        buttonGuardar.setOnClickListener {
            guardarSituacionMedica()
        }

        buttonCancelar.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun guardarSituacionMedica() {
        val condicionMedica = editCondicionMedica.text.toString().trim()
        val alergias = editAlergias.text.toString().trim()

        if (condicionMedica.isEmpty() && alergias.isEmpty()) {
            Toast.makeText(requireContext(), "Ingresa al menos un campo", Toast.LENGTH_SHORT).show()
            return
        }

        val request = SituacionMedicaRequest(
            usuario = userId,
            condicion_medica = condicionMedica,
            alergias = alergias
        )

        situMedicaController.createSituMedica(request)
            .enqueue(object : Callback<SituacionMedicaResponse> {
                override fun onResponse(
                    call: Call<SituacionMedicaResponse>,
                    response: Response<SituacionMedicaResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Información médica guardada", Toast.LENGTH_SHORT).show()
                        onSituacionMedicaAdded?.invoke()
                        dismiss()
                    } else {
                        Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SituacionMedicaResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    companion object {
        fun newInstance(userId: Int, onSituacionMedicaAdded: () -> Unit): AddSituacionMedicaDialog {
            return AddSituacionMedicaDialog().apply {
                arguments = Bundle().apply {
                    putInt("userId", userId)
                }
                this.onSituacionMedicaAdded = onSituacionMedicaAdded
            }
        }
    }
}
