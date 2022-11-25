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
            val pass =binding.LoginPass.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty())
            {
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if (it.isSuccessful)
                    {
                        val intent = Intent(this , MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()

                    }
                    else{
                        Toast.makeText(this,it.exception.toString() , Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                Toast.makeText(this, "empty ", Toast.LENGTH_SHORT).show()
            }

        }

        val createAcc = findViewById<TextView>(R.id.CreateAcc)
        val changeLang = findViewById<TextView>(R.id.ArabicLab)

        createAcc.setOnClickListener {
            val intent = Intent(this, CreateAcc::class.java)
            startActivity(intent)
        }
        changeLang.setOnClickListener{
            val intent = Intent(this , ArSignin::class.java)
            startActivity(intent)
        }



    }
}