package com.example.recetasapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecipeDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null,
    DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "recipes.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "recipes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_INGREDIENTS = "ingredients"
        private const val COLUMN_INSTRUCTIONS = "instructions"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_INGREDIENTS TEXT," +
                "$COLUMN_INSTRUCTIONS TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXIST $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertRecipes(recipe: Recipe) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, recipe.name)
            put(COLUMN_INGREDIENTS, recipe.ingredients)
            put(COLUMN_INSTRUCTIONS, recipe.instructions)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllRecipes(): List<Recipe> {
        val recipesList = mutableListOf<Recipe>()
        val db = readableDatabase
        val query = "SElECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val ingredients = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS))
            val instruction = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INSTRUCTIONS))

            val recipe = Recipe(id, name, ingredients, instruction)
            recipesList.add(recipe)
        }
        cursor.close()
        db.close()
        return recipesList
    }

    fun updateRecipe(recipe: Recipe) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, recipe.name)
            put(COLUMN_INGREDIENTS, recipe.ingredients)
            put(COLUMN_INSTRUCTIONS, recipe.instructions)
        }
        val whereClause = "$COLUMN_ID=?"
        val whereArgs = arrayOf(recipe.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getRecipeByID(recipeId: Int): Recipe {
        val db = readableDatabase
        val query= "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $recipeId"
        val cursor= db.rawQuery(query, null)
        cursor.moveToFirst()

        val id= cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val name= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val ingredients= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS))
        val instruction= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INSTRUCTIONS))

        cursor.close()
        db.close()
        return Recipe(id, name, ingredients, instruction)
    }

}