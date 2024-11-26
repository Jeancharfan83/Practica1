package com.example.recetasapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class RecipesAdapter(private var recipes: List<Recipe>, context: Context) :
    RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val ingredientsTextView: TextView = itemView.findViewById(R.id.ingredientsTextView)
        val instructionTextView: TextView = itemView.findViewById(R.id.instructionsTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateSaveButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.nameTextView.text = recipe.name
        holder.ingredientsTextView.text = recipe.ingredients
        holder.instructionTextView.text = recipe.instructions

        holder.updateButton.setOnClickListener{
            val intent= Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("recipe_id", recipe.id)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    fun refreshData(newRecipes: List<Recipe>){
        recipes=newRecipes
        notifyDataSetChanged()
    }
}