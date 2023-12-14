package com.example.biometricsattendance


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "biometric_attendance.db"
        const val DATABASE_VERSION = 1

        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_BIOMETRIC = "biometric"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUserTable = "CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_EMAIL TEXT," +
                "$COLUMN_PASSWORD TEXT," +
                "$COLUMN_BIOMETRIC TEXT" +
                ")"
        db?.execSQL(createUserTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades if needed
    }

    fun addUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, user.name)
            put(COLUMN_EMAIL, user.email)
            put(COLUMN_PASSWORD, user.password)
            put(COLUMN_BIOMETRIC, user.biometric)
        }
        db.insert(TABLE_USERS, null, values)
        db.close()
    }

    fun isEmailExists(email: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun getUser(email: String, password: String): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        val idIndex = cursor.getColumnIndex(COLUMN_ID)
        val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
        val emailIndex = cursor.getColumnIndex(COLUMN_EMAIL)
        val passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD)
        val biometricIndex = cursor.getColumnIndex(COLUMN_BIOMETRIC)

        Log.d("CursorInfo", "ID Index: $idIndex")
        Log.d("CursorInfo", "Name Index: $nameIndex")
        Log.d("CursorInfo", "Email Index: $emailIndex")
        Log.d("CursorInfo", "Password Index: $passwordIndex")
        Log.d("CursorInfo", "Biometric Index: $biometricIndex")


        User(id, name, userEmail, userPassword, biometric)
        } else {
            null
        }


    }
}

