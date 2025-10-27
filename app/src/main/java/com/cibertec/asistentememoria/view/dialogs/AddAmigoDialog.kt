package com.cibertec.asistentememoria.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.controller.AmigosController
import com.cibertec.asistentememoria.model.AmigosRequest
import com.cibertec.asistentememoria.model.AmigosResponse
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddAmigoDialog : DialogFragment() {

    private var userId: Int = 0
    private var onAmigoAdded: (() -> Unit)? = null

    private lateinit var editName: TextInputEditText
    private lateinit var editTelefono: TextInputEditText
    private val amigosController = AmigosController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments?.getInt("userId") ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_nuevo_amigo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editName = view.findViewById(R.id.editName)
        editTelefono = view.findViewById(R.id.editTelefono)

        val buttonGuardar = view.findViewById<Button>(R.id.buttonGuardar)
        val buttonCancelar = view.findViewById<Button>(R.id.buttonCancelar)

        buttonGuardar.setOnClickListener {
            guardarAmigo()
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

    private fun guardarAmigo() {
        val name = editName.text.toString().trim()
        val telefono = editTelefono.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(requireContext(), "Ingresa el nombre", Toast.LENGTH_SHORT).show()
            return
        }

        if (telefono.isEmpty()) {
            Toast.makeText(requireContext(), "Ingresa el teléfono", Toast.LENGTH_SHORT).show()
            return
        }

        val request = AmigosRequest(
            usuario = userId,
            nombre = name,
            telefono = telefono
        )

        amigosController.createAmigo(request)
            .enqueue(object : Callback<AmigosResponse> {
                override fun onResponse(
                    call: Call<AmigosResponse>,
                    response: Response<AmigosResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Amigo guardado", Toast.LENGTH_SHORT).show()
                        onAmigoAdded?.invoke()
                        dismiss()
                    } else {
                        Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AmigosResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    companion object {
        fun newInstance(userId: Int, onAmigoAdded: () -> Unit): AddAmigoDialog {
            return AddAmigoDialog().apply {
                arguments = Bundle().apply {
                    putInt("userId", userId)
                }
                this.onAmigoAdded = onAmigoAdded
            }
        }
    }
}
