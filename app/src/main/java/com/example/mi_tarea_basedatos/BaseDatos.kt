package com.example.mi_tarea_basedatos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "students.db"
        private const val DATABASE_VERSION = 1

        // Tabla y columnas
        const val TABLE_NAME = "students"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_AGE = "age"
        const val COLUMN_GRADE = "grade"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_AGE INTEGER NOT NULL,
                $COLUMN_GRADE TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTable)

        // Insertar 5 estudiantes
        val students = listOf(
            ContentValues().apply { put(COLUMN_NAME, "Juan"); put(COLUMN_AGE, 20); put(COLUMN_GRADE, "A") },
            ContentValues().apply { put(COLUMN_NAME, "Ana"); put(COLUMN_AGE, 21); put(COLUMN_GRADE, "B") },
            ContentValues().apply { put(COLUMN_NAME, "Luis"); put(COLUMN_AGE, 19); put(COLUMN_GRADE, "A") },
            ContentValues().apply { put(COLUMN_NAME, "María"); put(COLUMN_AGE, 22); put(COLUMN_GRADE, "C") },
            ContentValues().apply { put(COLUMN_NAME, "Carlos"); put(COLUMN_AGE, 23); put(COLUMN_GRADE, "B") }
        )
        students.forEach { db?.insert(TABLE_NAME, null, it) }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Métodos para consultar estudiantes
    fun getAllStudents(): List<String> {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        val students = mutableListOf<String>()

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE))
                val grade = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GRADE))
                students.add("Nombre: $name, Edad: $age, Grado: $grade")
            } while (cursor.moveToNext())
        }
        cursor.close()
        return students
    }

    fun getStudentByName(name: String): String? {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, "$COLUMN_NAME = ?", arrayOf(name), null, null, null)
        var student: String? = null

        if (cursor.moveToFirst()) {
            val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE))
            val grade = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GRADE))
            student = "Nombre: $name, Edad: $age, Grado: $grade"
        }
        cursor.close()
        return student
    }
}

