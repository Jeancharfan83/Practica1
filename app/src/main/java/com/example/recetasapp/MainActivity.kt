package com.example.recetasapp

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recetasapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: RecipeDatabaseHelper
    private lateinit var recipesAdapter: RecipesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = RecipeDatabaseHelper(this)
        recipesAdapter = RecipesAdapter(db.getAllRecipes(), this)

        binding.recipesRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.recipesRecyclerview.adapter = recipesAdapter


        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
        }

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        recipesAdapter.refreshData(db.getAllRecipes())
    }
}