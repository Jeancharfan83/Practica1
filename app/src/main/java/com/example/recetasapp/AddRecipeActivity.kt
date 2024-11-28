package com.example.recetasapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recetasapp.databinding.ActivityAddRecipeBinding

class AddRecipeActivity : AppCompatActivity() {


    private lateinit var  binding: ActivityAddRecipeBinding
    private lateinit var db: RecipeDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        db= RecipeDatabaseHelper(this)
        binding.saveButton.setOnClickListener{
            val name= binding.nameEditText.text.toString()
            val ingredients= binding.ingredientsEditText.text.toString()
            val instructions= binding.instructionsEditText.text.toString()
            val recipe= Recipe(0,name,ingredients,instructions)
            db.insertRecipes(recipe)
            finish()
            Toast.makeText(this, R.string.Deleted, Toast.LENGTH_SHORT).show()
        }
    }
    // Inflar el menú
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu) // Inflar el archivo XML de menú
        return true
    }

    // Manejar los eventos de los elementos del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_back -> {
                onBackPressed()
                return true
            }

            R.id.action_exit -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}