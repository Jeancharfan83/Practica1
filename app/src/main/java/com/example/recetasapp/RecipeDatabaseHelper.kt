package com.example.recetasapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecipeDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME= "recipes.db"
        private const val DATABASE_VERSION= 1
        private const val TABLE_NAME= "recipes"
        private const val COLUMN_ID= "id"
        private const val COLUMN_NAME= "name"
        private const val COLUMN_INGREDIENTS= "ingredients"
        private const val COLUMN_INSTRUCTIONS= "instructions"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_INGREDIENTS TEXT," +
                "$COLUMN_INGREDIENTS TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery= "DROP TABLE IF EXIST $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertRecipes(recipe: Recipe){
        val db=writableDatabase
        val values=ContentValues().apply {
            put(COLUMN_NAME, recipe.name)
            put(COLUMN_INGREDIENTS, recipe.ingredients)
            put(COLUMN_INSTRUCTIONS, recipe.instructions)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
}