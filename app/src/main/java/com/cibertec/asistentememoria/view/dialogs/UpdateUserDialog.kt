package com.cibertec.asistentememoria.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.controller.UserController
import com.cibertec.asistentememoria.model.UserResponse
import com.cibertec.asistentememoria.model.UsuarioRequestEdit
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.text.ifEmpty

class UpdateUserDialog : DialogFragment() {

    private lateinit var editNombre: TextInputEditText
    private lateinit var editApellido: TextInputEditText
    private lateinit var editTelefono: TextInputEditText

    private lateinit var editFechaNacimiento: TextInputEditText
    private lateinit var buttonGuardar: Button
    private lateinit var buttonCancelar: Button

    private var userId: Int = 0
    private var onUpdateSuccess: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_edit_user_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editNombre = view.findViewById(R.id.editNombre)
        editApellido = view.findViewById(R.id.editApellido)
        editTelefono = view.findViewById(R.id.editTelefono)
        editFechaNacimiento = view.findViewById(R.id.editFechaNacimiento)
        buttonGuardar = view.findViewById(R.id.buttonGuardar)
        buttonCancelar = view.findViewById(R.id.buttonCancelar)

        // Cargar datos actuales del usuario
        loadCurrentUserData()

        buttonCancelar.setOnClickListener {
            dismiss()
        }

        buttonGuardar.setOnClickListener {
            updateUser()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun loadCurrentUserData() {
        UserController().obtenerUser(userId).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                response.body()?.let { user ->
                    editNombre.setText(user.nombre)
                    editApellido.setText(user.apellido)
                    editTelefono.setText(user.telefono)
                    editFechaNacimiento.setText(user.fechaNacimiento)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error al cargar datos", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUser() {
        val nombre = editNombre.text.toString().trim()
        val apellido = editApellido.text.toString().trim()
        val telefono = editTelefono.text.toString().trim()
        val fechaNacimiento = editFechaNacimiento.text.toString().trim()

        if (nombre.isEmpty() || apellido.isEmpty()) {
            Toast.makeText(requireContext(), "Complete los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val userRequest = UsuarioRequestEdit(
            nombre = nombre,
            apellido = apellido,
            telefono = telefono.ifEmpty { null },
            fechaNacimiento = fechaNacimiento.ifEmpty { null }
        )

        UserController().updateUser(userId, userRequest).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show()
                    onUpdateSuccess?.invoke()
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "Error al actualizar usuario", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private const val USER_ID = "user_id"

        fun newInstance(userId: Int, onUpdateSuccess: () -> Unit): UpdateUserDialog {
            return UpdateUserDialog().apply {
                arguments = Bundle().apply {
                    putInt(USER_ID, userId)
                }
                this.userId = userId
                this.onUpdateSuccess = onUpdateSuccess
            }
        }
    }
}
