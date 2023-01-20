package com.example.hatrick

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hatrick.databinding.ActivityMainBinding
import com.example.hatrick.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Signin : AppCompatActivity() {
    lateinit var binding: ActivitySigninBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        binding.signinBtn.setOnClickListener {
            val email =binding.LoginEmail.text.toString()
            val password =binding.LoginPass.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty())
            {
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful)
                    {
                        val docexist =db.collection("users").document("${getCurrentUserID()}")
                        docexist.get().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val document = task.result
                                if(document != null) {
                                    if (document.exists()) {
                                        val intent = Intent(this , MainActivity::class.java)
                                        startActivity(intent)
                                    } else {
                                        Toast.makeText(this,"User doesn't exist" , Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Log.d("TAG", "Error: ", task.exception)
                            }
                        }
                    }
                    else{
                        Toast.makeText(this,"Incorrect email or password" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (email.isEmpty()){
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            }
            else if (password.isEmpty()){
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Please enter your email and password", Toast.LENGTH_SHORT).show()
            }
        }
        val createAcc = findViewById<TextView>(R.id.CreateAcc)

        createAcc.setOnClickListener {
            val intent = Intent(this, CreateAcc::class.java)
            startActivity(intent)
        }
        val forgetPassword = findViewById<TextView>(R.id.forgetPass)
        forgetPassword.setOnClickListener {
            val intent = Intent(this, ForgetPassword::class.java)
            startActivity(intent)
        }
    }
}