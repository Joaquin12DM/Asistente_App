package com.cibertec.asistentememoria.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.controller.InformacionPersonalController
import com.cibertec.asistentememoria.controller.SItuacionMedicaController
import com.cibertec.asistentememoria.controller.UserController
import com.cibertec.asistentememoria.model.InformacionPersonalResponse
import com.cibertec.asistentememoria.model.SituacionMedicaResponse
import com.cibertec.asistentememoria.model.UserResponse
import com.cibertec.asistentememoria.view.dialogs.AddInfoPersonalDialog
import com.cibertec.asistentememoria.view.dialogs.AddSituacionMedicaDialog
import com.cibertec.asistentememoria.view.dialogs.UpdateUserDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.asistentememoria.controller.AmigosController
import com.cibertec.asistentememoria.model.AmigosResponse
import com.cibertec.asistentememoria.view.adapters.AmigoAdapter
import com.cibertec.asistentememoria.view.dialogs.AddAmigoDialog
import com.cibertec.asistentememoria.controller.FamiliaresController
import com.cibertec.asistentememoria.model.FamiliaresResponse
import com.cibertec.asistentememoria.view.adapters.FamiliarAdapter
import com.cibertec.asistentememoria.view.dialogs.AddFamiliarDialog


class PerfilFragment : Fragment() {


    private var userId: Int = 0

    private lateinit var textUserName: TextView
    private lateinit var textUserEmail: TextView
    private lateinit var textTelefono: TextView
    private lateinit var textFechaNacimiento: TextView
    private lateinit var textHobbies: TextView
    private lateinit var textComidaFavorita: TextView
    private lateinit var textMusicaFavorita: TextView
    private lateinit var textColorFavorito: TextView
    private lateinit var textCondicionMedica: TextView
    private lateinit var textAlergias: TextView
    private lateinit var recyclerViewAmigos: RecyclerView
    private lateinit var amigoAdapter: AmigoAdapter
    private lateinit var recyclerViewFamiliares: RecyclerView
    private lateinit var familiarAdapter: FamiliarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments?.getInt(USER_ID, 0) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textUserName = view.findViewById(R.id.textUserName)
        textUserEmail = view.findViewById(R.id.textUserEmail)
        textTelefono = view.findViewById(R.id.textTelefono)
        textFechaNacimiento = view.findViewById(R.id.textFechaNacimiento)
        textHobbies = view.findViewById(R.id.textHobbies)
        textComidaFavorita = view.findViewById(R.id.textComidaFavorita)
        textMusicaFavorita = view.findViewById(R.id.textMusicaFavorita)
        textColorFavorito = view.findViewById(R.id.textColorFavorito)
        textCondicionMedica = view.findViewById(R.id.textCondicionMedica)
        textAlergias = view.findViewById(R.id.textAlergias)
        recyclerViewAmigos = view.findViewById(R.id.recyclerViewAmigos)
        recyclerViewAmigos.layoutManager = LinearLayoutManager(requireContext())
        amigoAdapter = AmigoAdapter(emptyList())
        recyclerViewAmigos.adapter = amigoAdapter
        recyclerViewFamiliares = view.findViewById(R.id.recyclerViewFamiliares)
        recyclerViewFamiliares.layoutManager = LinearLayoutManager(requireContext())
        familiarAdapter = FamiliarAdapter(emptyList())
        recyclerViewFamiliares.adapter = familiarAdapter




        obtenerUser()
        obtenerInfoPersonal()
        obtenerSituacionMedica()
        obtenerAmigos()
        obtenerFamiliares()

        val buttonEditPersonalInfo = view.findViewById<ImageButton>(R.id.buttonEditPersonalInfo)
        buttonEditPersonalInfo.setOnClickListener {
            showEditDialog()
        }

        val buttonAddSobreMi = view.findViewById<ImageButton>(R.id.buttonAddSobreMi)
        buttonAddSobreMi.setOnClickListener {
            val dialog = AddInfoPersonalDialog.newInstance(userId) {
                obtenerInfoPersonal()
            }
            dialog.show(childFragmentManager, "AddInfoPersonalDialog")
        }

        val buttonAddInfoMedica = view.findViewById<ImageButton>(R.id.buttonAddInfoMedica)
        buttonAddInfoMedica.setOnClickListener {
            val dialog = AddSituacionMedicaDialog.newInstance(userId) {
                obtenerSituacionMedica()
            }
            dialog.show(childFragmentManager, "AddSituacionMedicaDialog")
        }

        val buttonAddAmigo = view.findViewById<ImageButton>(R.id.buttonAddAmigo)
        buttonAddAmigo.setOnClickListener {
            val dialog = AddAmigoDialog.newInstance(userId) {
                obtenerAmigos()
            }
            dialog.show(childFragmentManager, "AddAmigoDialog")
        }

        val buttonAddFamiliar = view.findViewById<ImageButton>(R.id.buttonAddFamiliar)
        buttonAddFamiliar.setOnClickListener {
            val dialog = AddFamiliarDialog.newInstance(userId) {
                obtenerFamiliares()
            }
            dialog.show(childFragmentManager, "AddFamiliarDialog")
        }
    }


    private fun showEditDialog() {
        val dialog = UpdateUserDialog.newInstance(userId) {
            obtenerUser()
        }
        dialog.show(childFragmentManager, "UpdateUserDialog")
    }





    private fun obtenerUser() {
        UserController().obtenerUser(userId).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                response.body()?.let { user ->
                    textUserName.text = "${user.nombre ?: ""} ${user.apellido ?: ""}".trim()
                    textUserEmail.text = user.email ?: ""
                    textTelefono.text = "Teléfono\n${user.telefono ?: "No especificado"}"
                    textFechaNacimiento.text = "Fecha de nacimiento\n${user.fechaNacimiento ?: "No especificado"}"
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error al obtener el usuario", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun obtenerInfoPersonal() {
        InformacionPersonalController().obtenerInfoPerso(userId)
            .enqueue(object : Callback<InformacionPersonalResponse> {
                override fun onResponse(
                    call: Call<InformacionPersonalResponse>,
                    response: Response<InformacionPersonalResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { info ->
                            textHobbies.text = "Hobbies\n${info.hobbies}"
                            textComidaFavorita.text = "Comida favorita\n${info.comidaFavorita}"
                            textMusicaFavorita.text = "Música favorita\n${info.musicaFavorita}"
                            textColorFavorito.text = "Color favorito\n${info.colorFavorito}"
                        }
                    }
                }

                override fun onFailure(call: Call<InformacionPersonalResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error al obtener información personal", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun obtenerSituacionMedica() {
        SItuacionMedicaController().obtenerSituMedica(userId)
            .enqueue(object : Callback<SituacionMedicaResponse> {
                override fun onResponse(
                    call: Call<SituacionMedicaResponse>,
                    response: Response<SituacionMedicaResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { situacion ->
                            textCondicionMedica.text = "Condición Médica\n${situacion.condicion_medica}"
                            textAlergias.text = "Alergias\n${situacion.alergias}"
                        }
                    }
                }

                override fun onFailure(call: Call<SituacionMedicaResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error al obtener situación médica", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun obtenerAmigos() {
        AmigosController().obtenerAmigos(userId)
            .enqueue(object : Callback<List<AmigosResponse>> {
                override fun onResponse(
                    call: Call<List<AmigosResponse>>,
                    response: Response<List<AmigosResponse>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { amigos ->
                            amigoAdapter.updateAmigos(amigos)
                        }
                    }
                }

                override fun onFailure(call: Call<List<AmigosResponse>>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error al obtener amigos", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun obtenerFamiliares() {
        FamiliaresController().obtenerfamiliar(userId)
            .enqueue(object : Callback<List<FamiliaresResponse>> {
                override fun onResponse(
                    call: Call<List<FamiliaresResponse>>,
                    response: Response<List<FamiliaresResponse>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { familiares ->
                            familiarAdapter.updateFamiliares(familiares)
                        }
                    }
                }

                override fun onFailure(call: Call<List<FamiliaresResponse>>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error al obtener familiares", Toast.LENGTH_SHORT).show()
                }
            })
    }





    companion object {
        private const val USER_ID = "user_id"
        fun newInstance(userId: Int): PerfilFragment {
            return PerfilFragment().apply {
                arguments = Bundle().apply {
                    putInt(USER_ID, userId)
                }
            }
        }
    }


}



