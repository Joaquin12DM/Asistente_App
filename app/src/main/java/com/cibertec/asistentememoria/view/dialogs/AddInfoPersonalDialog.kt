package com.cibertec.asistentememoria.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.controller.InformacionPersonalController
import com.cibertec.asistentememoria.model.InformacionPersonalRequest
import com.cibertec.asistentememoria.model.InformacionPersonalResponse
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddInfoPersonalDialog : DialogFragment() {

    private var userId: Int = 0
    private var onInfoPersonalAdded: (() -> Unit)? = null

    private lateinit var editHobbies: TextInputEditText
    private lateinit var editComida: TextInputEditText
    private lateinit var editMusica: TextInputEditText
    private lateinit var editColor: TextInputEditText
    private val infoPersoController = InformacionPersonalController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments?.getInt("userId") ?: 0
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_nueva_infopersonal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editHobbies = view.findViewById(R.id.editHobbies)
        editComida = view.findViewById(R.id.editComida)
        editMusica = view.findViewById(R.id.editMusica)
        editColor = view.findViewById(R.id.editColor)

        val buttonGuardar = view.findViewById<Button>(R.id.buttonGuardar)
        val buttonCancelar = view.findViewById<Button>(R.id.buttonCancelar)

        buttonGuardar.setOnClickListener {
            guardarInfoPersonal()
        }

        buttonCancelar.setOnClickListener {
            dismiss()
        }
    }

    private fun guardarInfoPersonal() {
        val hobbies = editHobbies.text.toString().trim()
        val comida = editComida.text.toString().trim()
        val musica = editMusica.text.toString().trim()
        val color = editColor.text.toString().trim()

        if (hobbies.isEmpty() && comida.isEmpty() && musica.isEmpty() && color.isEmpty()) {
            Toast.makeText(requireContext(), "Ingresa al menos un campo", Toast.LENGTH_SHORT).show()
            return
        }

        val infoPersona = InformacionPersonalRequest(
            usuario = userId,
            hobbies = hobbies,
            comidaFavorita = comida,
            musicaFavorita = musica,
            colorFavorito = color
        )

        infoPersoController.createInfoPerso(infoPersona)
            .enqueue(object : Callback<InformacionPersonalResponse> {
                override fun onResponse(
                    call: Call<InformacionPersonalResponse>,
                    response: Response<InformacionPersonalResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Información guardada", Toast.LENGTH_SHORT).show()
                        onInfoPersonalAdded?.invoke()
                        dismiss()
                    } else {
                        Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<InformacionPersonalResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }



    companion object {
        fun newInstance(userId: Int, onInfoPersonalAdded: () -> Unit): AddInfoPersonalDialog {
            return AddInfoPersonalDialog().apply {
                arguments = Bundle().apply {
                    putInt("userId", userId)
                }
                this.onInfoPersonalAdded = onInfoPersonalAdded
            }
        }
    }
}
