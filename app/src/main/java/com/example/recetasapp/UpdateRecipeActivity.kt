package com.example.recetasapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recetasapp.databinding.ActivityUpdateRecipeBinding

class UpdateRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateRecipeBinding
    private lateinit var db: RecipeDatabaseHelper
    private var recipeId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUpdateRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = RecipeDatabaseHelper(this)

        recipeId = intent.getIntExtra("recipe_id", -1)
        if (recipeId == -1) {
            finish()
            return
        }

        val recipe = db.getRecipeByID(recipeId)
        binding.updateNameEditText.setText(recipe.name)
        binding.updateIngredientsEditText.setText(recipe.ingredients)
        binding.updateInstructionsEditText.setText(recipe.instructions)

        binding.updateSaveButton.setOnClickListener {
            val newName = binding.updateNameEditText.text.toString()
            val newIngredients = binding.updateIngredientsEditText.text.toString()
            val newInstructions = binding.updateInstructionsEditText.text.toString()
            val updateRecipe= Recipe(recipeId, newName, newIngredients, newInstructions)
            db.updateRecipe(updateRecipe)
            finish()
            Toast.makeText(this, R.string.Changes, Toast.LENGTH_SHORT).show()

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
                onSupportNavigateUp()
                return true
            }

            R.id.action_exit -> {
                finishAffinity()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}