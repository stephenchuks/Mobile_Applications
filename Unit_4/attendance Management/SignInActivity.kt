package com.example.biometricsattendance

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        dbHelper = DatabaseHelper(this)

        val emailEditText: EditText = findViewById(R.id.editTextEmailSignIn)
        val passwordEditText: EditText = findViewById(R.id.editTextPasswordSignIn)
        val signInButton: Button = findViewById(R.id.buttonSignIn)

        signInButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            val user = dbHelper.getUser(email, password)

            if (user != null) {
                // Sign in successful, navigate to the home screen
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Sign in failed, show error message
                Toast.makeText(this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
