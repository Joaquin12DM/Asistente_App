package com.cibertec.asistentememoria.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.controller.MomentoController
import com.cibertec.asistentememoria.model.MomentoResponse
import com.cibertec.asistentememoria.view.adapters.RecuerdosAdapter
import com.cibertec.asistentememoria.view.dialogs.AddRecuerdoDialog
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecuerdosFragment : Fragment() {

    private var userId: Int = 0
    private lateinit var recyclerViewRecuerdos: RecyclerView
    private lateinit var textEmptyRecuerdos: TextView
    private lateinit var adapterRecuerdos: RecuerdosAdapter
    private val controllerMomento = MomentoController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments?.getInt(USER_ID, 0) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recuerdos, container, false)

        recyclerViewRecuerdos = view.findViewById(R.id.recyclerViewRecuerdos)
        textEmptyRecuerdos = view.findViewById(R.id.textEmptyRecuerdos)

        recyclerViewRecuerdos.layoutManager = LinearLayoutManager(requireContext())
        adapterRecuerdos = RecuerdosAdapter(emptyList())
        recyclerViewRecuerdos.adapter = adapterRecuerdos


        val addButton = view.findViewById<MaterialButton>(R.id.buttonAddRecuerdo)
        addButton.setOnClickListener {
            val dialog = AddRecuerdoDialog.newInstance(userId)
            dialog.onRecuerdoCreado = {
                cargarMomentos()
            }
            dialog.show(parentFragmentManager, "AddRecuerdoDialog")
        }

        cargarMomentos()
        return view
    }

    private fun cargarMomentos() {
        controllerMomento.obtenerMomentos(userId)
            .enqueue(object : Callback<List<MomentoResponse>> {
                override fun onResponse(
                    call: Call<List<MomentoResponse>>,
                    response: Response<List<MomentoResponse>>
                ) {
                    if (!isAdded) return
                    if (response.isSuccessful) {
                        val lista = response.body() ?: emptyList()
                        if (lista.isEmpty()) {
                            recyclerViewRecuerdos.visibility = View.GONE
                            textEmptyRecuerdos.visibility = View.VISIBLE
                        } else {
                            recyclerViewRecuerdos.visibility = View.VISIBLE
                            textEmptyRecuerdos.visibility = View.GONE
                            adapterRecuerdos.actualizarLista(lista)
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error al obtener recuerdos: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<MomentoResponse>>, exception: Throwable) {
                    if (!isAdded) return
                    Toast.makeText(
                        requireContext(),
                        "Fallo en la conexión: ${exception.localizedMessage ?: exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    companion object {
        private const val USER_ID = "user_id"
        fun newInstance(userId: Int): RecuerdosFragment {
            return RecuerdosFragment().apply {
                arguments = Bundle().apply {
                    putInt(USER_ID, userId)
                }
            }
        }
    }
}
