package com.example.recetasapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recetasapp.databinding.ActivityNameUserBinding

class UserNameActivity:AppCompatActivity() {

    private lateinit var binding: ActivityNameUserBinding
    private lateinit var prefs: Pref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityNameUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prefs = Pref(this)

        checkUser()

        initUI()
    }

    fun checkUser(){
        if (prefs.getName().isNotEmpty()){
            goToApp()
        }
    }

    private fun initUI() {
        binding.btnContinue.setOnClickListener {
            accessToApp()
        }
    }

    fun accessToApp(){
        val usarname = binding.etName.text.toString()
        if(usarname.isNotEmpty()){
            prefs.saveName(usarname)
            goToApp()
        } else {
            binding.etName.error = "Necesitas un nombre de usuario"
        }

    }

    private fun goToApp() {
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

}