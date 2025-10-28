package com.cibertec.asistentememoria.view.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.cibertec.asistentememoria.R
import com.cibertec.asistentememoria.view.fragments.HomeFragment
import com.cibertec.asistentememoria.view.fragments.PerfilFragment
import com.cibertec.asistentememoria.view.fragments.RecuerdosFragment
import com.cibertec.asistentememoria.view.fragments.RemindersFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences("asistenteApp_prefs", MODE_PRIVATE)
        val userId = prefs.getInt("user_id", 0)





        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> setCurrentFragment(HomeFragment())

                R.id.menu_reminders -> {
                    val fragment = RemindersFragment.newInstance(userId)
                    setCurrentFragment(fragment)
                }
                R.id.menu_recuerdos -> {
                    val fragment = RecuerdosFragment.newInstance(userId)
                    setCurrentFragment(fragment)
                }
                R.id.menu_perfil ->{
                    val fragment = PerfilFragment.newInstance(userId)
                    setCurrentFragment(fragment)
                }
            }
            true
        }
        setCurrentFragment(HomeFragment())









        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}