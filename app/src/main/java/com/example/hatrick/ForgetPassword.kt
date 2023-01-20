package com.example.hatrick

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ForgetPassword : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        firebaseAuth = FirebaseAuth.getInstance()
        val checkEmail = findViewById<Button>(R.id.checkEmail)
        val email = findViewById<EditText>(R.id.emailBox)
        checkEmail.setOnClickListener {
            val UserFireData = FirebaseFirestore.getInstance()
            UserFireData.collection("users").whereEqualTo("email", email.text.toString()).get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        firebaseAuth.sendPasswordResetEmail(email.text.toString()).addOnSuccessListener {
                            Toast.makeText(this, "Please Check Your Email", Toast.LENGTH_SHORT)
                                .show()
                        }.addOnFailureListener{
                            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(this, "Email Not Found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
        findViewById<TextView>(R.id.cancelBtn).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}