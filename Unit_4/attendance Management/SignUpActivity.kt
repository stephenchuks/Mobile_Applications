package com.example.biometricsattendance

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        dbHelper = DatabaseHelper(this)

        val nameEditText: EditText = findViewById(R.id.editTextName)
        val emailEditText: EditText = findViewById(R.id.editTextEmail)
        val passwordEditText: EditText = findViewById(R.id.editTextPassword)
        val signUpButton: Button = findViewById(R.id.buttonSignUp)

        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (isValidEmail(email)) {
                if (!dbHelper.isEmailExists(email)) {
                    val user = User(name, email, password, "")
                    dbHelper.addUser(user)
                    Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show()
                    // Redirect to the next screen or perform other actions
                } else {
                    Toast.makeText(this, "Email already exists!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Invalid email!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        // Simple email validation
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
