package com.example.mi_tarea_basedatos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class listarEstudiante : AppCompatActivity() {

    private lateinit var dbHelper: BaseDatos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_estudiante)

        dbHelper = BaseDatos(this)

        val etStudentName = findViewById<EditText>(R.id.etStudentName)
        val btnListAll = findViewById<Button>(R.id.btnListAll)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnListAll.setOnClickListener {
            val students = dbHelper.getAllStudents()
            tvResult.text = students.joinToString("\n")
        }

        btnSearch.setOnClickListener {
            val name = etStudentName.text.toString().trim()
            if (name.isNotEmpty()) {
                val student = dbHelper.getStudentByName(name)
                tvResult.text = student ?: "Estudiante no encontrado"
            } else {
                tvResult.text = "Por favor, ingresa un nombre"
            }
        }
    }
}
