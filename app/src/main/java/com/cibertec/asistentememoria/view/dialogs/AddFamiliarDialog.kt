package com.cibertec.asistentememoria.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.controller.FamiliaresController
import com.cibertec.asistentememoria.model.FamiliaresRequest
import com.cibertec.asistentememoria.model.FamiliaresResponse
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFamiliarDialog : DialogFragment() {

    private var userId: Int = 0
    private var onFamiliarAdded: (() -> Unit)? = null

    private lateinit var editName: TextInputEditText
    private lateinit var editRelacion: TextInputEditText
    private lateinit var editTelefono: TextInputEditText
    private val familiaresController = FamiliaresController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments?.getInt("userId") ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_nuevo_familiar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editName = view.findViewById(R.id.editName)
        editRelacion = view.findViewById(R.id.editRelacion)
        editTelefono = view.findViewById(R.id.editTelefono)

        val buttonGuardar = view.findViewById<Button>(R.id.buttonGuardar)
        val buttonCancelar = view.findViewById<Button>(R.id.buttonCancelar)

        buttonGuardar.setOnClickListener {
            guardarFamiliar()
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

    private fun guardarFamiliar() {
        val nombre = editName.text.toString().trim()
        val relacion = editRelacion.text.toString().trim()
        val telefono = editTelefono.text.toString().trim()

        if (nombre.isEmpty()) {
            Toast.makeText(requireContext(), "Ingresa el nombre", Toast.LENGTH_SHORT).show()
            return
        }

        if (relacion.isEmpty()) {
            Toast.makeText(requireContext(), "Ingresa la relación", Toast.LENGTH_SHORT).show()
            return
        }

        if (telefono.isEmpty()) {
            Toast.makeText(requireContext(), "Ingresa el teléfono", Toast.LENGTH_SHORT).show()
            return
        }

        val familiar = FamiliaresRequest(
            usuario = userId,
            nombre = nombre,
            relacion = relacion,
            telefono = telefono
        )

        familiaresController.createFamiliar(familiar)
            .enqueue(object : Callback<FamiliaresResponse> {
                override fun onResponse(
                    call: Call<FamiliaresResponse>,
                    response: Response<FamiliaresResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Familiar guardado", Toast.LENGTH_SHORT).show()
                        onFamiliarAdded?.invoke()
                        dismiss()
                    } else {
                        Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<FamiliaresResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    companion object {
        fun newInstance(userId: Int, onFamiliarAdded: () -> Unit): AddFamiliarDialog {
            return AddFamiliarDialog().apply {
                arguments = Bundle().apply {
                    putInt("userId", userId)
                }
                this.onFamiliarAdded = onFamiliarAdded
            }
        }
    }
}
