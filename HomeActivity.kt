package com.example.cell
import android.app.Activity
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    private lateinit var dbHelper: CelDataBaseHelper
    private lateinit var db: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        dbHelper = CelDataBaseHelper(this)
        db = dbHelper.writableDatabase

        val cursor1 = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='School'", null)
        val tableExists = cursor1.moveToFirst()
        cursor1.close()

        if (tableExists) {
            // Table exists, do something
        } else {
            // Table does not exist, do something else

            val CREATE_CONTACTS_TABLE = "CREATE TABLE School (" +
                    "_id INTEGER PRIMARY KEY," +
                    "name TEXT," +
                    "number TEXT," +
                    "email TEXT" +

                    ");"

            db.execSQL(CREATE_CONTACTS_TABLE)
        }





        val addButton = findViewById<Button>(R.id.addButton)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val Sports = findViewById<EditText>(R.id.Sports)
        val Time = findViewById<EditText>(R.id.Time)

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        val box = findViewById<LinearLayout>(R.id.box)



        val new: FloatingActionButton = findViewById(R.id.floatingActionButton)
        var isRotated = false


        new.setOnClickListener {

            if (isRotated) {
                new.rotation = 0.0f
                isRotated = false
                box.visibility = View.GONE

            } else {
                new.rotation = 45.0f
                isRotated = true
                box.visibility = View.VISIBLE

            }

        }






        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val number = Sports.text.toString()
            val email = Time.text.toString()

            val values = ContentValues().apply {
                put("name", name)
                put("number", number)
                put("email", email)
            }

            db.insert("School", null, values)



            updateTableView()

        }










        val cursor = db.query("School", arrayOf("_id", "name", "number", "email"), null, null, null, null, null)
        while (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            val name = cursor.getString(1)
            val number = cursor.getString(2)
            val email = cursor.getString(3)

            val tableRow = LayoutInflater.from(this).inflate(R.layout.table_sample, null) as TableRow
            tableRow.findViewById<TextView>(R.id.name).text = name
            tableRow.findViewById<TextView>(R.id.Sport).text = number
            tableRow.findViewById<TextView>(R.id.time).text = email

            val removeButton = tableRow.findViewById<TableRow>(R.id.removeButton)

            removeButton.setOnClickListener {
                db.delete("contacts", "_id = ?", arrayOf(id.toString()))
                tableLayout.removeView(tableRow)
            }

            tableLayout.addView(tableRow)
        }
        cursor.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

    private fun updateTableView() {

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)

        // Clear the current rows from the table view
        for (i in tableLayout.childCount - 1 downTo 1) {
            tableLayout.removeView(tableLayout.getChildAt(i))
        }
        // Query the database to get all rows
        val cursor = db.rawQuery("SELECT * FROM School", null)

        // Iterate over the rows in the cursor and add them to the table view
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val number = cursor.getString(cursor.getColumnIndexOrThrow("number"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))

            val tableRow = LayoutInflater.from(this).inflate(R.layout.table_sample, null) as TableRow
            tableRow.findViewById<TextView>(R.id.name).text = name
            tableRow.findViewById<TextView>(R.id.Sport).text = number
            tableRow.findViewById<TextView>(R.id.time).text = email

            val removeButton = tableRow.findViewById<TableRow>(R.id.removeButton)

            removeButton.setOnClickListener {
                // Remove the row from the database
                db.delete("School", "name = ?", arrayOf(name))
                // Remove the row from the table view
                tableLayout.removeView(tableRow)
            }

            tableLayout.addView(tableRow)
        }

        // Close the cursor to free up resources
        cursor.close()
    }






}

