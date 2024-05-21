package com.example.cell


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CelDataBaseHelper (context: Context) : SQLiteOpenHelper(context, "School.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE_QUERY = "CREATE TABLE my_table (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, number TEXT, time TEXT)"
        db.execSQL(CREATE_TABLE_QUERY)    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades
    }
}

