package com.example.recetasapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recetasapp.databinding.ActivityAddRecipeBinding
import com.example.recetasapp.databinding.ActivityMainBinding

class AddRecipeActivity : AppCompatActivity() {


    private lateinit var  binding: ActivityAddRecipeBinding
    private lateinit var db: RecipeDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_recipe)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recip)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding=ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db= RecipeDatabaseHelper(this)
        binding.saveButton.setOnClickListener{
            val name= binding.nameEditText.text.toString()
            val ingredients= binding.ingredientsEditText.text.toString()
            val instructions= binding.instructionsEditText.text.toString()
            val recipe= Recipe(0,name,ingredients,instructions)
            db.insertRecipes(recipe)
            finish()
            Toast.makeText(this, "Recipe Saved", Toast.LENGTH_SHORT).show()
        }
    }
}