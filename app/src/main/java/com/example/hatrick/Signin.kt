package com.example.hatrick

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hatrick.databinding.ActivityMainBinding
import com.example.hatrick.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth

class Signin : AppCompatActivity() {
    lateinit var binding: ActivitySigninBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.signinBtn.setOnClickListener {
            val email =binding.LoginEmail.text.toString()
            val password =binding.LoginPass.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty())
            {
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful)
                    {
                        val intent = Intent(this , MainActivity::class.java)
                        startActivity(intent)
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