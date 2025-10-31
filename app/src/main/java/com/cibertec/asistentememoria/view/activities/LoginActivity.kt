package com.cibertec.asistentememoria.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.controller.LoginController
import com.cibertec.asistentememoria.controller.UserController
import com.cibertec.asistentememoria.model.UserRequest
import com.cibertec.asistentememoria.model.UserRequestLogin
import com.cibertec.asistentememoria.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.apply

class LoginActivity : AppCompatActivity() {

    private lateinit var textAppName: View
    private lateinit var registerLayout: View
    private lateinit var emailLo: EditText
    private lateinit var passwordLo: EditText
    private lateinit var loginBtn: View
    private lateinit var noAccount: View
    private lateinit var createAccount: View
    private lateinit var subtitle: View


    private lateinit var emailRe: EditText
    private lateinit var passwordRe: EditText
    private lateinit var nombres: EditText
    private lateinit var apellidos: EditText
    private lateinit var fechaNacimiento: EditText

    private lateinit var registerBtn: View
    private lateinit var btnVolver:View
    val controllerUser = UserController()
    val controllerLogin = LoginController()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        textAppName = findViewById(R.id.textAppName)
        createAccount = findViewById(R.id.textCreateAccount)
        registerLayout = findViewById(R.id.registerLayout)
        loginBtn = findViewById(R.id.buttonLogin)
        noAccount = findViewById(R.id.textNoAccount)
        subtitle = findViewById(R.id.textSubtitle)
        emailLo = findViewById(R.id.editTextEmailLo)
        passwordLo = findViewById(R.id.editTextPasswordLo)

        nombres = findViewById(R.id.editTextNombreRe)
        apellidos = findViewById(R.id.editTextRe)
        fechaNacimiento = findViewById(R.id.editTextFechaNacimientoRe)
        emailRe = findViewById(R.id.editTextEmailRe)
        passwordRe = findViewById(R.id.editTextPasswordRe)
        registerBtn = findViewById(R.id.buttonRegister)
        btnVolver = findViewById(R.id.buttonBack)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        createAccount.setOnClickListener {
            showRegister()
        }

        registerBtn.setOnClickListener {
            registerUser()
            restoreLogin()
        }

        btnVolver.setOnClickListener {
            restoreLogin()
        }

        loginBtn.setOnClickListener {
            loginUser()
        }




    }

    private fun registerUser(){
        val nombresRe = nombres.text.toString().trim()
        val apellidosRe = apellidos.text.toString().trim()
        val fechaNacimientoRe = fechaNacimiento.text.toString().trim()
        val emailRe = emailRe.text.toString().trim()
        val passwordRe = passwordRe.text.toString().trim()


        val newUser = UserRequest(
            nombre = nombresRe,
            apellido = apellidosRe,
            email = emailRe,
            contrasena = passwordRe,
            fechaNacimiento = fechaNacimientoRe
        )

        controllerUser.createUser(newUser).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, "Error en la respuesta: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error al crear usuario", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun loginUser(){
        val email = emailLo.text.toString().trim()
        val password = passwordLo.text.toString().trim()

        val userLogin = UserRequestLogin(
            email = email,
            contrasena = password
        )

        controllerLogin.login(userLogin).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {

                        val prefs = getSharedPreferences("asistenteApp_prefs", MODE_PRIVATE)
                        prefs.edit().putInt("user_id", user.id).apply()

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }

                } else {
                    Toast.makeText(this@LoginActivity, "Error en la respuesta: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error al iniciar sesion", Toast.LENGTH_SHORT).show()
            }
        })


    }











    private fun showRegister() {
        emailLo.visibility = View.GONE
        passwordLo.visibility = View.GONE
        loginBtn.visibility = View.GONE
        noAccount.visibility = View.GONE
        createAccount.visibility = View.GONE
        subtitle.visibility = View.GONE
        textAppName.visibility = View.GONE
        registerLayout.visibility = View.VISIBLE
    }

    private fun restoreLogin() {
        registerLayout.visibility = View.GONE
        emailLo.visibility = View.VISIBLE
        passwordLo.visibility = View.VISIBLE
        loginBtn.visibility = View.VISIBLE
        noAccount.visibility = View.VISIBLE
        createAccount.visibility = View.VISIBLE
        subtitle.visibility = View.VISIBLE
        textAppName.visibility = View.VISIBLE
    }






}