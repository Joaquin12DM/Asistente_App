package com.cibertec.asistentememoria.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.controller.RecordatorioController
import com.cibertec.asistentememoria.model.RecordatorioResponse
import com.cibertec.asistentememoria.model.RecordatorioTipoRequest
import com.cibertec.asistentememoria.view.adapters.RecordatorioAdapter
import com.cibertec.asistentememoria.view.dialogs.AddRecordatorioDialog
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemindersFragment : Fragment() {

    private var userId: Int = 0

    private lateinit var recyclerViewMedicamentos: RecyclerView
    private lateinit var recyclerViewCitas: RecyclerView
    private lateinit var adapterMedicamentos: RecordatorioAdapter
    private lateinit var adapterCitas: RecordatorioAdapter

    private val controllerTipoRecordatorio = RecordatorioController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments?.getInt(USER_ID, 0) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reminders, container, false)

        recyclerViewMedicamentos = view.findViewById(R.id.recyclerViewMedicamentos)
        recyclerViewCitas = view.findViewById(R.id.recyclerViewCitas)

        recyclerViewMedicamentos.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewCitas.layoutManager = LinearLayoutManager(requireContext())

        adapterMedicamentos = RecordatorioAdapter(emptyList())
        adapterCitas = RecordatorioAdapter(emptyList())

        recyclerViewMedicamentos.adapter = adapterMedicamentos
        recyclerViewCitas.adapter = adapterCitas

        val addButton = view.findViewById<MaterialButton>(R.id.buttonAddReminder)
        addButton.setOnClickListener {
            val dialog = AddRecordatorioDialog.newInstance(userId)
            dialog.onRecordatorioCreado = {
                cargarRecordatorios()
            }
            dialog.show(parentFragmentManager, "NuevoRecuerdoDialog")
        }

        cargarRecordatorios()
        return view
    }

    private fun cargarRecordatorios() {
        cargarRecordatoriosPorTipo("Medicamento")
        cargarRecordatoriosPorTipo("Cita Médica")
    }


    private fun cargarRecordatoriosPorTipo(tipo: String) {
        val idUsuario = userId
        val tipoRecordatorio = RecordatorioTipoRequest(
            usuarioId = idUsuario,
            tipo = tipo
        )

        controllerTipoRecordatorio.buscarPorTipo(tipoRecordatorio)
            .enqueue(object : Callback<List<RecordatorioResponse>> {
                override fun onResponse(call: Call<List<RecordatorioResponse>>, response: Response<List<RecordatorioResponse>>) {
                    if (response.isSuccessful) {
                        val lista = response.body() ?: emptyList()
                        if (tipo.equals("Medicamento", ignoreCase = true)) {
                            adapterMedicamentos.actualizarLista(lista)
                        } else {
                            adapterCitas.actualizarLista(lista)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error al obtener recordatorios: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<RecordatorioResponse>>, exception: Throwable) {
                    if (!isAdded) return
                    Toast.makeText(requireContext(), "Fallo en la conexión: ${exception.localizedMessage ?: exception.message}", Toast.LENGTH_SHORT).show()
                }

            })
    }

    companion object {
        private const val USER_ID = "user_id"
        fun newInstance(userId: Int): RemindersFragment {
            return RemindersFragment().apply {
                arguments = Bundle().apply {
                    putInt(USER_ID, userId)
                }
            }
        }
    }


}